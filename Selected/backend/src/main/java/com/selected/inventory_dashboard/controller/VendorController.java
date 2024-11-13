package com.selected.inventory_dashboard.controller;

import com.selected.inventory_dashboard.dtovo.req.VendorRequest;
import com.selected.inventory_dashboard.dtovo.res.VendorResponse;
import com.selected.inventory_dashboard.service.interfaces.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<List<VendorResponse>> getALlVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<VendorResponse>> getVendorsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(vendorService.getAllVendorsWithLimit(limit));
    }

    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorRequest vendorRequest) {
        return ResponseEntity.ok(vendorService.insertNewVendor(vendorRequest));
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable Integer vendorId, @RequestBody VendorRequest vendorRequest) {
        return ResponseEntity.ok(vendorService.updateVendor(vendorId, vendorRequest));
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Boolean> deleteVendor(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(vendorService.deleteVendor(vendorId));
    }
}
