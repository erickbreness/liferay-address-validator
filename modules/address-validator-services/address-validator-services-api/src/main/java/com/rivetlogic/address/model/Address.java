/**
 * Copyright (C) 2005-present Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.rivetlogic.address.model;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

public class Address {
    
    private String fullAddress;
    private String country;
    private String countryId;
    private String region;
    private String regionId;
    private String city;
    private String street;
    private String postalCode;
    private String longitude;
    private String latitude;
    
    public String getFullAddress() {
        if (null != fullAddress && !fullAddress.isEmpty()) {
            return fullAddress;
        }
        else{
            return toString();
        }
    }
    
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    @Override
    public String toString() {
        
        StringBuilder requestedAddress = new StringBuilder();
        
        if (Validator.isNotNull(street)) {
            
            requestedAddress.append(street);
        }
        
        if (Validator.isNotNull(city)) {
            
            addAddressSeparator(requestedAddress);
            
            requestedAddress.append(city);
        }
        
        if (Validator.isNotNull(region)) {
            
            addAddressSeparator(requestedAddress);
            
            requestedAddress.append(region);
        }
        
        if (Validator.isNotNull(postalCode)) {
            
            if (Validator.isNotNull(region)) {
                requestedAddress.append(StringPool.SPACE);
            } else {
                addAddressSeparator(requestedAddress);
            }
            
            requestedAddress.append(postalCode);
        }
        
        if (Validator.isNotNull(country)) {
            
            addAddressSeparator(requestedAddress);
            
            requestedAddress.append(country);
        }
        
        return requestedAddress.toString();
    }
    
    private StringBuilder addAddressSeparator(StringBuilder addressString) {
        if (addressString.length() > 0) {
            addressString.append(StringPool.COMMA_AND_SPACE);
        }
        return addressString;
    }
    
}
