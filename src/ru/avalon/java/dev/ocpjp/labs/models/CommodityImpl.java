/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.ocpjp.labs.models;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Павлик
 */
public class CommodityImpl implements Commodity {
    
    public final static class CommodityImplBuilder implements CommodityBuilder {
        
        CommodityImplBuilder(){}
        
        private CommodityImpl dummy = new CommodityImpl();

        @Override
        public CommodityBuilder code(String code) {
            dummy.code = code;
            return this;
        }

        @Override
        public CommodityBuilder vendorCode(String vendorCode) {
            dummy.vendorCode = vendorCode;
            return this;
        }

        @Override
        public CommodityBuilder name(String name) {
            dummy.name = name;
            return this;
        }

        @Override
        public CommodityBuilder price(double price) {
            dummy.price = price;
            return this;
        }

        @Override
        public CommodityBuilder residue(int residue) {
            dummy.residue = residue;
            return this;
        }

        @Override
        public Commodity build() {
            requireNonNull(dummy.code);
            requireNonNull(dummy.vendorCode);
            requireNonNull(dummy.name);
            requireNonNull(dummy.price);
            requireNonNull(dummy.residue);
            CommodityImpl result = dummy;
            dummy = new CommodityImpl();
            return result;
        }
        
    }
    
    private String code;
    
    private String vendorCode;
    
    private String name;
    
    private double price;
    
    private int residue;
    

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getVendorCode() {
        return vendorCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getResidue() {
        return residue;
    }
    
    static CommodityImplBuilder builder (){
        return new CommodityImplBuilder();
    }
    
}
