package com.leyou.order.enumdto;

import lombok.AllArgsConstructor;

/**
 * @author river
 * @title: InvoiceType
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/2114:55
 */
@AllArgsConstructor
public enum InvoiceType {
    NO_INVOICE(0,"无发票"),//无发票
    GENERAL_INVOICE(1,"普通发票"),//普通发票
    ELECTRONIC_INVOICE(2,"电子发票"),//电子发票
    VAT_INVOICE(3,"增值税发票")//增值税发票
    ;
    private  Integer code;
    private  String  invoiceMessage;

    public Integer getCode() {
        return code;
    }

    public String getInvoiceMessage() {
        return invoiceMessage;
    }
}
