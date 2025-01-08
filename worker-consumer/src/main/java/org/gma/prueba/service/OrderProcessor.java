package org.gma.prueba.service;

import lombok.RequiredArgsConstructor;
import org.gma.prueba.model.OrderDTO;
import org.gma.prueba.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProcessor {

    @Autowired
    private final GraphQlApiService service;

    public void process(OrderDTO order) {
        // Validate if customer exists
        if (isValidOrder(order)) {
            service.addPurchaseOrder(order);
        }
    }

    private boolean isValidOrder(OrderDTO order) {
        try {
            if (service.getCustomerById(order.customer().id()) == null) {
                return false;
            }

            for (ProductDTO prodcut : order.product()) {
                if (service.getProductById(prodcut.id()) == null) {
                    return false;
                }
            }

            return true;
        } catch (Exception ex) {
            // log exception here
            return false;
        }
    }

}
