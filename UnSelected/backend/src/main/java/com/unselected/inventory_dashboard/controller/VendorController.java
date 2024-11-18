package com.unselected.inventory_dashboard.controller;

import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.dto.VendorRequest;
import com.unselected.inventory_dashboard.dto.VendorResponse;
import com.unselected.inventory_dashboard.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    @Autowired
    private Dao dao;

    @GetMapping
    public ResponseEntity<List<VendorResponse>> getALlVendors() {
        return ResponseEntity.ok(dao.getAllVendors().stream()
                .map(this::mapVendorToVendorResponse).toList());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<VendorResponse>> getVendorsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(dao.getAllVendors().stream().limit(limit)
                .map(this::mapVendorToVendorResponse).toList());
    }

    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorRequest vendorRequest) {
        final Integer vendorId = dao.createVendor(buildVendor(vendorRequest));
        return ResponseEntity.ok(mapVendorToVendorResponse(dao.getVendor(vendorId)));
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable Integer vendorId, @RequestBody VendorRequest vendorRequest) {
        dao.updateVendor(buildVendor(vendorRequest, vendorId));
        return ResponseEntity.ok(mapVendorToVendorResponse(dao.getVendor(vendorId)));
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Boolean> deleteVendor(@PathVariable Integer vendorId) {
        dao.deleteVendor(vendorId);
        return ResponseEntity.ok(dao.selectLimit(vendorId) == null);
    }

    private VendorResponse mapVendorToVendorResponse(final Vendor vendor) {
        return new VendorResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getEmail(),
                vendor.getPhone()
        );
    }

    private Vendor buildVendor(final VendorRequest vendorRequest) {
        return buildVendor(vendorRequest, null);
    }

    private Vendor buildVendor(final VendorRequest vendorRequest,
                               final Integer vendorId) {
        final Vendor.VendorBuilder vendorBuilder =  Vendor.builder().id(vendorId).name(vendorRequest.name())
                .email(vendorRequest.email()).phone(vendorRequest.phone())
                .createTime(Date.from(Instant.now()))
                .updateTime(Date.from(Instant.now()));

        if (vendorId != null) {
            vendorBuilder.id(vendorId);
        }

        return vendorBuilder.build();
    }
}
