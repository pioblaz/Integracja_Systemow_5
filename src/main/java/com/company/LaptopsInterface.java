package com.company;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface LaptopsInterface {
    @WebMethod int laptopsByProducent(String producent);
    @WebMethod Object[][] laptopsByMatryca(String matryca);
    @WebMethod int laptopsByProporcje(String proporcje);
}
