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

package com.rivetlogic.address.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.rivetlogic.address.model.Address;


public class AddressUtil {
    
    public static final String ADDRESS_STREET_JSON_LABEL = "street";
    public static final String ADDRESS_CITY_JSON_LABEL = "city";
    public static final String ADDRESS_REGION_JSON_LABEL = "region";
    public static final String ADDRESS_REGION_ID_JSON_LABEL = "regionId";
    public static final String ADDRESS_COUNTRY_JSON_LABEL = "country";
    public static final String ADDRESS_COUNTRY_ID_JSON_LABEL = "countryId";
    public static final String ADDRESS_POSTAL_CODE_JSON_LABEL = "postalCode";
    public static final String ADDRESS_LONGITUDE_JSON_LABEL = "longitude";
    public static final String ADDRESS_LATITUDE_JSON_LABEL = "latitude";
    public static final String ADDRESS_DISPLAY_JSON_LABEL = "displayAddress";
    
    public static JSONObject addressToJSONObject(Address address){
        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
        jsonObject.put(ADDRESS_STREET_JSON_LABEL, address.getStreet());
        jsonObject.put(ADDRESS_CITY_JSON_LABEL, address.getCity());
        jsonObject.put(ADDRESS_REGION_JSON_LABEL, address.getRegion());
        jsonObject.put(ADDRESS_REGION_ID_JSON_LABEL, address.getRegionId());
        jsonObject.put(ADDRESS_POSTAL_CODE_JSON_LABEL, address.getPostalCode());
        jsonObject.put(ADDRESS_COUNTRY_JSON_LABEL, address.getCountry());
        jsonObject.put(ADDRESS_COUNTRY_ID_JSON_LABEL, address.getCountryId());
        jsonObject.put(ADDRESS_LONGITUDE_JSON_LABEL, address.getLongitude());
        jsonObject.put(ADDRESS_LATITUDE_JSON_LABEL, address.getLatitude());
        jsonObject.put(ADDRESS_DISPLAY_JSON_LABEL,address.getFullAddress());
        return jsonObject;
    }
    
}
