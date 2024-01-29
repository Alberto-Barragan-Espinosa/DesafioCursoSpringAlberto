package com.tlaxcala.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tlaxcala.dto.ConsultDTO;
import com.tlaxcala.dto.ExamDTO;
import com.tlaxcala.dto.SpecialtyDTO;
import com.tlaxcala.exception.CustomErrorResponse;
import com.tlaxcala.model.Specialty;
import com.tlaxcala.service.ISpecialtyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController//categoriza esta clase como un Controlador REST
@RequestMapping("/specialties")//define la ruta padre para todos los servicios de la clase - buena practica: la ruta padre siempre sea en plural
@RequiredArgsConstructor//SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
@SecurityRequirement(name = "Mediapp")//AUTORIZACION PARA EJECUCION DE SERVICIOS QUE REQUIEREN TOKEN
public class SpecialtyController {

    //SINGLETON - definicion de la fachada directamente mediante constructor pero en LOMBOK- SUSTITUCION DE AUTOWIRED
    private final ISpecialtyService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    private SpecialtyDTO convertToDto(Specialty obj) {
        return mapper.map(obj, SpecialtyDTO.class);
    }

    private Specialty convertToEntity(SpecialtyDTO dto) {
        return mapper.map(dto, Specialty.class);
    }

    //@Operation(summary = "Obtencion del catalogo completo de especialidades", description= "findAll - Encontrar todo")
    @Operation(summary = "findAll - Encontrar todos los registros de especialidad", description= "Obtencion del catalogo completo de especialidades")
    @ApiResponse(responseCode = "200", description = "OK - El servicio responde con todos los registros existentes de especialidades")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @GetMapping//Mapeo de servicio REST
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        // podemos ocupar el api de programación funcional para el manejo de listas
        // mediante
        // la propiedad stream
        // cuando se hace referencia de un método dentro de un lambda yo puedo aplicar
        // una abreviación
        // List<SpecialtyDTO> listExample = service.findAll().stream().map(e ->
        // convertToDto(e)).toList();
        List<SpecialtyDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        // List<SpecialtyDTO> list = service.findAll().stream().map(e ->
        /*
         * List<SpecialtyRecord> list = service.findAll()
         * .stream().
         * map(e ->
         * new SpecialtyRecord(e.getIdSpecialty(), e.getFirstName(), e.getLastName(),
         * e.getDni(),
         * e.getAddress(), e.getPhone(), e.getEmail())
         * ).toList();
         */
        /*
         * {
         * 
         * SpecialtyDTO dto = new SpecialtyDTO();
         * dto.setIdSpecialty(e.getIdSpecialty());
         * dto.setFirstName(e.getFirstName());
         * dto.setLastName(e.getLastName());
         * dto.setDni(e.getDni());
         * dto.setPhone(e.getPhone());
         * dto.setEmail(e.getEmail());
         * dto.setAddress(e.getAddress());
         * return dto;
         * }
         * ).toList();
         */
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "save - Guardar registro de especialidad", description= "Almacenamiento de un nuevo registro de especialidad en la base de datos")
    @ApiResponse(responseCode = "201", description = "CREATED - Se creó de manera exitosa el registro de la especialidad y devuelve como respuesta un objeto de especialidad vacio")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST - Error al almacenar el nuevo registro, por ejemplo; a causa de algun parametro en ''null''", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @PostMapping//Mapeo de servicio REST
    public ResponseEntity<SpecialtyDTO> save(@Valid @RequestBody SpecialtyDTO dto) {
        Specialty obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    
    //PARA ENVIAR UN OBJETO DISTINTO EN LA RESPUESTA DE DEMOSTRACION DEL SWAGGER IU OPEN API => 
    @Operation(summary = "findById - Encontrar especialidad por numero identificador", description= "Obtencion de la especialidad por su numero de indentificador en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK - Devuelve la especialidad")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @ApiResponse(responseCode = "404", description = "NOT FOUND - La entidad de especialidad con el numero de indentificador establecido como parametro de busqueda no se encuentró", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @GetMapping("/{id}")//Mapeo de servicio REST
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) {
        Specialty obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @Operation(summary = "update - Modificar un registro de especialidad", description= "Modificacion de la especialidad usando como referencia del item a modificar su numero de identificacion en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK - Devuelve la especialidad editada")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @ApiResponse(responseCode = "404", description = "NOT FOUND - La entidad de especialidad con el numero de indentificador establecido como parametro de busqueda para ser editado no se encuentró", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @PutMapping("/{id}")//Mapeo de servicio REST
    public ResponseEntity<Specialty> update(@PathVariable("id") Integer id, @RequestBody SpecialtyDTO dto) throws Exception {
        Specialty obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Operation(summary = "delete - Eliminar un registro de especialidad", description= "Eliminacion un registro especialidad en la base de datos usando su numero de identificacion como referencia para identificar el item a borrar")
    @ApiResponse(responseCode = "204", description = "NO CONTENT - Devuelve un objeto ''null'' (respuesta vacia) de la especialidad ya que ha sido eliminada de la base de datos exitosamente")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @ApiResponse(responseCode = "404", description = "NOT FOUND - La entidad de especialidad con el numero de indentificador establecido como parametro de busqueda para ser eliminado no se encuentró", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @DeleteMapping("/{id}")//Mapeo de servicio REST
    public ResponseEntity<Specialty> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // hateoas -> para tipo get
    //@Operation(summary = "Obtencion de la especialidad con datos extras", description= "findById - Encontrar por")
    @Operation(summary = "findByHateoas - Peticion de busqueda de especialidad complementada con HATEOAS", description= "Servicio para realizar la busqueda de un registro de especialidad complementada con enlaces de los recursos HATEOAS de interaccion con el servidor")
    @ApiResponse(responseCode = "200", description = "OK - Devuelve la informacion de la especialidad complementada con los enlaces HATEOAS")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Usuario no autorizado para realizar la operacion", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @ApiResponse(responseCode = "404", description = "NOT FOUND - La entidad de especialidad con el numero de indentificador establecido como parametro de busqueda para obtener su informacion y sus enlaces HATEOAS no se encontró en la base de datos", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    @GetMapping("/hateoas/{id}")//Mapeo de servicio REST
    public EntityModel<SpecialtyDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<SpecialtyDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // la salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id)); // la generación del link

        resource.add(link1.withRel("specialty-info1")); // agregamos el link con una llave
        resource.add(link1.withRel("specialty-info2"));

        return resource;
    }

}

