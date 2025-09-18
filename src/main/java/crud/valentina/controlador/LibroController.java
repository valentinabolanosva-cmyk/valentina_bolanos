package crud.valentina.controlador;

import crud.valentina.modelo.Libro;
import crud.valentina.servicio.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseEntity<Libro> crearLibro(@Valid @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.guardarLibro(libro));
    }

    @GetMapping
    public ResponseEntity<List<Libro>> obtenerLibros(@RequestParam(required = false) String autor) {
        if (autor != null && !autor.isEmpty()) {
            return ResponseEntity.ok(libroService.buscarPorAutor(autor));
        }
        return ResponseEntity.ok(libroService.obtenerTodosLosLibros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroService.obtenerLibroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        return libroService.actualizarLibro(id, libro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        return libroService.eliminarLibro(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
