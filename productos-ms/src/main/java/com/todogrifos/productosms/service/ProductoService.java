package com.todogrifos.productosms.service;

import com.todogrifos.productosms.dto.ProductoCreateDTO;
import com.todogrifos.productosms.dto.ProductoDTO;
import com.todogrifos.productosms.dto.ProductoUpdateDTO;
import com.todogrifos.productosms.exception.ProductoNotFoundException;
import com.todogrifos.productosms.exception.SkuDuplicadoException;
import com.todogrifos.productosms.model.Producto;
import com.todogrifos.productosms.repository.CategoriaRepository;
import com.todogrifos.productosms.repository.MarcaRepository;
import com.todogrifos.productosms.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    // POST
    public ProductoDTO registrarProducto(ProductoCreateDTO dto) {
        if (productoRepository.existsBySku(dto.getSku())) {
            throw new SkuDuplicadoException("El SKU " + dto.getSku() + " ya está registrado.");
        }
        Producto producto = convertirAEntidad(dto);
        producto.setActivo(true);

        producto.setMarca(marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada")));

        producto.setCategoria(categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));

        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    // GET
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("No existe producto con ID " + id));
        return convertirADTO(producto);
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream().map(this::convertirADTO).toList();
    }

    // PUT
    public ProductoDTO actualizarProducto(Long id, ProductoUpdateDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() ->
                        new ProductoNotFoundException("Producto no encontrado.")
        );

        // no se modifica ni SKU ni la ID por integridad de negocio
        existente.setNombre(dto.getNombre());
        existente.setActivo(dto.getActivo());
        existente.setPrecio(dto.getPrecio());
        existente.setGarantiaMeses(dto.getGarantiaMeses());
        existente.setDescripcion(dto.getDescripcion());
        // se eligió no modificar tampoco categoria ni marca para mantener consistencia de datos
        Producto actualizado = productoRepository.save(existente);

        return convertirADTO(actualizado);

    }

    // DELETE
    public void borrarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() ->
                        new ProductoNotFoundException("Producto no encontrado.")
                );
        productoRepository.delete(producto);
    }

    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository
                .findByNombreContainingIgnoreCase(nombre).stream().map(this::convertirADTO).toList();
    }

    public ProductoDTO buscarPorSku(String sku) {
        return productoRepository.findBySku(sku).map(this::convertirADTO).orElseThrow(() ->
                        new ProductoNotFoundException("Producto no encontrado.")
                );
    }

    public List<ProductoDTO> listarPorMarca(Long marcaId) {
        return productoRepository.findByMarca_Id(marcaId).stream().map(this::convertirADTO).toList();
    }

    public List<ProductoDTO> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId).stream().map(this::convertirADTO).toList();
    }

    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue().stream().map(this::convertirADTO).toList();
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();

        dto.setId(producto.getId());
        dto.setSku(producto.getSku());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setActivo(producto.getActivo());
        dto.setGarantiaMeses(producto.getGarantiaMeses());
        dto.setMarca(producto.getMarca().getNombre());
        dto.setCategoria(producto.getCategoria().getNombre());

        return dto;
    }

    private Producto convertirAEntidad(ProductoCreateDTO dto) {

        Producto producto = new Producto();

        producto.setSku(dto.getSku());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setActivo(dto.getActivo());
        producto.setGarantiaMeses(dto.getGarantiaMeses());

        return producto;
    }

}
