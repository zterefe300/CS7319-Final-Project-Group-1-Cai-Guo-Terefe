package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.dtovo.req.VendorRequest;
import com.selected.inventory_dashboard.dtovo.res.VendorResponse;
import com.selected.inventory_dashboard.exception.VendorContactInfoException;
import com.selected.inventory_dashboard.exception.VendorNameException;
import com.selected.inventory_dashboard.persistence.dao.VendorMapper;
import com.selected.inventory_dashboard.persistence.entity.Vendor;
import com.selected.inventory_dashboard.service.interfaces.VendorService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {
    final private VendorMapper vendorMapper;

    public VendorServiceImpl(final VendorMapper vendorMapper) {
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorResponse> getAllVendors() {
        return vendorMapper.selectAll().stream().map(this::mapVendorToVendorResponse).toList();
    }

    @Override
    public List<VendorResponse> getAllVendorsWithLimit(Integer limit) {
        return vendorMapper.selectAll().stream().limit(limit).map(this::mapVendorToVendorResponse).toList();
    }

    @Override
    public VendorResponse insertNewVendor(final VendorRequest vendorRequest) {
        validateVendorNameExistence(vendorRequest);
        validateVendorContactInfoExistence(vendorRequest);

        final Integer vendorId = vendorMapper.insert(buildVendor(vendorRequest));
        return mapVendorToVendorResponse(vendorMapper.selectByPrimaryKey(vendorId));
    }

    @Override
    public VendorResponse updateVendor(final Integer vendorId, final VendorRequest vendorRequest) {
        validateVendorNameExistence(vendorRequest);
        validateVendorContactInfoExistence(vendorRequest);

        vendorMapper.updateByPrimaryKey(buildVendor(vendorRequest,vendorId));
        return mapVendorToVendorResponse(vendorMapper.selectByPrimaryKey(vendorId));
    }

    @Override
    public boolean deleteVendor(final Integer vendorId) {
        vendorMapper.deleteByPrimaryKey(vendorId);
        return vendorMapper.selectByPrimaryKey(vendorId) == null;
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

    private void validateVendorNameExistence(final VendorRequest vendorRequest) {
        if (vendorRequest.name() == null || vendorRequest.name().isEmpty()) {
            throw new VendorNameException();
        }
    }

    private void validateVendorContactInfoExistence(final VendorRequest vendorRequest) {
        if (vendorRequest.email() == null || vendorRequest.email().isEmpty() &&
                vendorRequest.phone() == null || vendorRequest.phone().isEmpty()) {
            throw new VendorContactInfoException();
        }
    }
}