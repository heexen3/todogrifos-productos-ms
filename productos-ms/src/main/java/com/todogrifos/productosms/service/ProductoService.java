package com.todogrifos.productosms.service;

import com.todogrifos.productosms.exception.ProductoNotFoundException;
import com.todogrifos.productosms.exception.SkuDuplicadoException;
import com.todogrifos.productosms.model.Producto;
import com.todogrifos.productosms.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // PUT
    public Producto registrarProducto(Producto producto) {
        if (productoRepository.existsBySku(producto.getSku())) {
            throw new SkuDuplicadoException("El SKU " + producto.getSku() + " ya está registrado.");
        }
        producto.setActivo(true);

        return productoRepository.save(producto);
    }

    // GET
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("No existe producto con ID " + id));
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // PUT
    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado.")
        );

        // no se modifica ni SKU ni la ID por integridad de negocio
        existente.setNombre(producto.getNombre());
        existente.setMarca(producto.getMarca());
        existente.setActivo(producto.getActivo());
        existente.setPrecio(producto.getPrecio());
        existente.setCategoria(producto.getCategoria());
        existente.setGarantiaMeses(producto.getGarantiaMeses());
        existente.setDescripcion(producto.getDescripcion());

        return productoRepository.save(existente);

    }

    // DELETE
    public void borrarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado.")
                );
        productoRepository.delete(producto);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository
                .findByNombreContainingIgnoreCase(nombre);
    }

    public Producto buscarPorSku(String sku) {
        return productoRepository.findBySku(sku).orElseThrow(() ->
                        new RuntimeException("Producto no encontrado.")
                );
    }

    public List<Producto> listarPorMarca(Long marcaId) {
        return productoRepository.findByMarca_Id(marcaId);
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId);
    }

    public List<Producto> listarProductosActivos() {
        return productoRepository.findByActivoTrue();
    }
}
