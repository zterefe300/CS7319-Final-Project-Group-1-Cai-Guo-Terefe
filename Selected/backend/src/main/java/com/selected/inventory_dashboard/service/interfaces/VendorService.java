package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.VendorRequest;
import com.selected.inventory_dashboard.dtovo.res.VendorResponse;

import java.util.List;

public interface VendorService {
    List<VendorResponse> getAllVendors();
    List<VendorResponse> getAllVendorsWithLimit(Integer limit);
    VendorResponse insertNewVendor(VendorRequest vendorRequest);
    VendorResponse updateVendor(Integer vendorId, VendorRequest vendorRequest);
    boolean deleteVendor(Integer vendorId);
}
