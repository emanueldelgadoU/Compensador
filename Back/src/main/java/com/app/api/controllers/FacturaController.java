package com.app.api.controllers;

import java.util.List;
import java.time.LocalDateTime;

import com.app.api.models.CompensacionModel;
import com.app.api.models.FacturaModel;
import com.app.api.models.UserModel;
import com.app.api.models.TransferenciaModel;
import com.app.api.repositories.FacturaRepository;
import com.app.api.repositories.UserRepository;
import com.app.api.services.FacturaService;
import com.app.api.services.UserService;
import com.app.api.services.TransferenciaService;
import com.app.api.services.CompensacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FacturaController {
    private final FacturaService facturaService;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private CompensacionService compensacionService;


    @PostMapping("/envioformulario")
    public void envioFormulario(@RequestBody Map<String, Object> request) {
        List<Map<String, Object>> itemInvoices = (List<Map<String, Object>>) request.get("itemInvoices");
        for (Map<String, Object> invoice : itemInvoices) {
            Integer invoiceId = (Integer) invoice.get("id");
            facturaService.eliminarFacturaPorId(invoiceId.longValue());
        }



        List<Map<String, Object>> itemGhost = (List<Map<String, Object>>) request.get("itemGhost");
        for (Map<String, Object> ghost : itemGhost) {
            Integer ghostId = (Integer) ghost.get("id");
            Integer ghostAmount = (Integer) ghost.get("amount");

            // Obtener la factura de la base de datos por su ID
            FacturaModel factura = facturaService.obtenerFacturaPorId(ghostId.longValue());

            // Verificar si la factura existe
            if (factura != null) {
                // Obtener el importe actual de la factura
                Integer importeActual = factura.getImporte();

                // Calcular el nuevo importe sumando el importe actual y el valor de "amount" de la factura fanstasma
                Integer nuevoImporte = importeActual + ghostAmount;

                // Actualizar el importe de la factura en la base de datos
                factura.setImporte(nuevoImporte);
                facturaService.guardarFactura(factura);
            }
        }

        List<Map<String, Object>> transferenciaList = (List<Map<String, Object>>) request.get("transferencia");
        for (Map<String, Object> transferencia : transferenciaList) {
            Double importe = ((Integer) transferencia.get("importe")).doubleValue();
            String idUsuarioString = (String) transferencia.get("id_usuario");
            Integer idUsuario = Integer.parseInt(idUsuarioString);


            // Obtener la fecha actual
            LocalDateTime fechaActual = LocalDateTime.now();

            // Crear un objeto TransferenciaModel y asignar los valores
            TransferenciaModel nuevaTransferencia = new TransferenciaModel();
            nuevaTransferencia.setImporte(importe);
            nuevaTransferencia.setUser(userService.obtenerUsuarioPorId(idUsuario.longValue()));
            nuevaTransferencia.setFecha(fechaActual);

            // Guardar la transferencia en la base de datos
            transferenciaService.guardarTransferencia(nuevaTransferencia);


            //COMPENSACION
            List<Map<String, Object>> compensacionList = (List<Map<String, Object>>) request.get("compensacion");
            for (Map<String, Object> compensacion : compensacionList) {
                Integer itemId = (Integer) compensacion.get("itemId");
                Integer amount = (Integer) compensacion.get("amount");

                // Obtener la fecha actual como un objeto Date
                Date fechaActualCompensacion = new Date();

                // Obtener el usuario por el ID
                UserModel user = userService.obtenerUsuarioPorId(itemId.longValue());

                // Crear un objeto CompensacionModel y asignar los valores
                CompensacionModel nuevaCompensacion = new CompensacionModel();
                nuevaCompensacion.setFecha(fechaActualCompensacion);
                nuevaCompensacion.setImporte(amount.doubleValue());
                nuevaCompensacion.setUser(user);

                // Guardar la compensacion en la base de datos
                compensacionService.guardarCompensacion(nuevaCompensacion);
            }


        }
    }



    @GetMapping("/facturas")
    public ResponseEntity<Map<String, Object>> obtenerTodasLasFacturas() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> itemInvoices = new ArrayList<>();

        List<FacturaModel> facturas = facturaService.obtenerTodasLasFacturas();

        for (FacturaModel factura : facturas) {
            Map<String, Object> itemInvoice = new HashMap<>();
            itemInvoice.put("idFactura", factura.getId());
            itemInvoice.put("itemId", factura.getUser().getId());
            itemInvoice.put("invoiceNum", factura.getNumeroFactura());
            itemInvoice.put("amount", factura.getImporte());
            itemInvoice.put("comment", factura.getComentario());
            itemInvoices.add(itemInvoice);
        }

        response.put("itemInvoices", itemInvoices);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/facturas/{userId}")
    public ResponseEntity<List<FacturaModel>> getFacturasById(@PathVariable("userId") Long userId) {
        List<FacturaModel> facturas = facturaRepository.findByUserId(userId);
        return ResponseEntity.ok(facturas);
    }


    @GetMapping("/facturascompensar")
        public ResponseEntity<Map<String, Object>> getFacturas(@RequestParam("userId") Long userId) {
            Map<String, Object> response = new HashMap<>();

            // Construir la lista de items
            List<Map<String, Object>> items = new ArrayList<>();

            // Primer objeto en items
            Map<String, Object> item1 = new HashMap<>();
            item1.put("id", 1);
            item1.put("username", userRepository.findById(1L).orElse(new UserModel()).getUsername());
            items.add(item1);

            // Obtener el usuario actual
            Optional<UserModel> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                UserModel currentUser = userOptional.get();

                // Segundo objeto en items
                Map<String, Object> item2 = new HashMap<>();
                item2.put("id", currentUser.getId());
                item2.put("username", currentUser.getUsername());
                items.add(item2);

                // Construir la lista de itemInvoices
                List<Map<String, Object>> itemInvoices = new ArrayList<>();

                // Obtener las facturas del usuario actual
                List<FacturaModel> facturas = facturaRepository.findByUserId(currentUser.getId());
                for (FacturaModel factura : facturas) {
                    Map<String, Object> itemInvoice = new HashMap<>();
                    itemInvoice.put("id", factura.getId());
                    itemInvoice.put("itemId", currentUser.getId());
                    itemInvoice.put("invoiceNum", factura.getNumeroFactura());
                    itemInvoice.put("amount", factura.getImporte());
                    itemInvoice.put("comment", factura.getComentario());
                    itemInvoices.add(itemInvoice);
                }
                // Obtener las facturas del usuario con ID 1 (con el que hay que compensar)
                List<FacturaModel> facturasPepe = facturaRepository.findByUserId(1L);
                for (FacturaModel facturaPepe : facturasPepe) {
                    Map<String, Object> itemInvoicePepe = new HashMap<>();
                    itemInvoicePepe.put("id", facturaPepe.getId());
                    itemInvoicePepe.put("itemId", 1);
                    itemInvoicePepe.put("invoiceNum", facturaPepe.getNumeroFactura());
                    itemInvoicePepe.put("amount", facturaPepe.getImporte());
                    itemInvoicePepe.put("comment", facturaPepe.getComentario());
                    itemInvoices.add(itemInvoicePepe);
                }



                response.put("itemInvoices", itemInvoices);
            }

            response.put("items", items);

            return ResponseEntity.ok(response);
        }
    }

