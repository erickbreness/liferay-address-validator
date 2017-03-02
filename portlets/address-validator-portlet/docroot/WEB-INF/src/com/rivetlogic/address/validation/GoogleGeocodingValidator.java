/**
 * Copyright (C) 2005-2014 Rivet Logic Corporation.
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

package com.rivetlogic.address.validation;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Region;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.rivetlogic.address.model.Address;
import com.rivetlogic.address.validation.AddressValidator;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GoogleGeocodingValidator implements AddressValidator {
    
    private final static Log _log = LogFactoryUtil.getLog(GoogleGeocodingValidator.class);
    
    private final static String REQUEST_URL = "https://maps.googleapis.com/maps/api/geocode/xml?sensor=false";
    private final static String REQUEST_ADDRESS_PARAMETER = "&address=";
    private final static String REQUEST_COMPONENTS_PARAMETER = "&components=";
    private final static String REQUEST_COMPONENT_COUNTRY = "country";
    private final static String REQUEST_COMPONENT_POSTAL_CODE = "postal_code";
    private final static String RESPONSE_XPATH_STATUS = "//GeocodeResponse/status";
    private final static String RESPONSE_XPATH_RESULTS = "//GeocodeResponse/result";
    private static final String RESPONSE_XPATH_FORMATED_ADDRESS = "formatted_address";
    private static final String RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE = "address_component[type='%s']/short_name";
    private static final String RESPONSE_XPATH_POSTAL_CODE = "postal_code";
    private static final String RESPONSE_XPATH_POSTAL_COUNTRY = "country";
    private static final String RESPONSE_XPATH_POSTAL_REGION = "administrative_area_level_1";
    private static final String RESPONSE_XPATH_POSTAL_CITY = "locality";
    private static final String RESPONSE_XPATH_POSTAL_ROUTE = "route";
    private static final String RESPONSE_XPATH_POSTAL_STREET_NUMBER = "street_number";
    private static final String RESPONSE_XPATH_LONG = "geometry/location/lng";
    private static final String RESPONSE_XPATH_LATITUDE = "geometry/location/lat";  
    private final static String RESPONSE_RESULT_OK = "OK";
    private final static String RESPONSE_RESULT_ZERO_RESULTS = "ZERO_RESULTS";
    private final static String STREET_ADDRESS_FORMAT = "%s %s";
    
    private StringBuilder addAddressSeparator(StringBuilder addressString) {
        if (addressString.length() > 0) {
            addressString.append(StringPool.COMMA_AND_SPACE);
        }
        return addressString;
    }
    
    private String getAddressStringforRequest(Address address) {
        
        StringBuilder requestedAddress = new StringBuilder();
        
        if (Validator.isNotNull(address.getStreet())) {
            
            requestedAddress.append(address.getStreet());
        }
        
        if (Validator.isNotNull(address.getCity())) {
            
            addAddressSeparator(requestedAddress);
            
            requestedAddress.append(address.getCity());
        }
        
        if (Validator.isNotNull(address.getRegion())) {
            
            addAddressSeparator(requestedAddress);
            
            requestedAddress.append(address.getRegion());
        }
        
        return requestedAddress.toString();
        
    }
    
    private StringBuilder addComponentsSeparator(StringBuilder componentsString) {
        if (componentsString.length() > 0) {
            componentsString.append(StringPool.PIPE);
        }
        
        return componentsString;
    }
    
    private String getComponentStringForRequest(Address address) {
        
        StringBuilder requestedComponents = new StringBuilder();
        
        if (Validator.isNotNull(address.getCountry())) {
            
            addComponentsSeparator(requestedComponents);
            
            requestedComponents.append(REQUEST_COMPONENT_COUNTRY + StringPool.COLON + address.getCountry());
        }
        
        if (Validator.isNotNull(address.getPostalCode())) {
            
            addComponentsSeparator(requestedComponents);
            
            requestedComponents.append(REQUEST_COMPONENT_POSTAL_CODE + StringPool.COLON + address.getPostalCode());
        }
        
        return requestedComponents.toString();
    }
    
    private String getRequestURL(Address address) throws IOException {
        
        StringBuilder request = new StringBuilder(REQUEST_URL);
        
        String addressRequest = URLEncoder.encode(getAddressStringforRequest(address), StringPool.UTF8);
        if (addressRequest.length() > 0) {
            request.append(REQUEST_ADDRESS_PARAMETER + addressRequest);
        }
        
        String componentsRequest = getComponentStringForRequest(address);
        if (componentsRequest.length() > 0) {
            request.append(REQUEST_COMPONENTS_PARAMETER + componentsRequest);
        }
        
        return request.toString();
    }
    
    private List<Address> getAddressesFromXML(Document resultDocument) throws SystemException {
        
        List<Address> response = new ArrayList<Address>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        try {
            String status = (String) xpath.evaluate(RESPONSE_XPATH_STATUS, resultDocument, XPathConstants.STRING);
            
            if (status.equals(RESPONSE_RESULT_OK)) {
                
                NodeList results = (NodeList) xpath.evaluate(RESPONSE_XPATH_RESULTS, resultDocument,
                        XPathConstants.NODESET);
                
                for (int i = 0, n = results.getLength(); i < n; i++) {
                    
                    Node result = results.item(i);
                    
                    String formatedAddress = (String) xpath.evaluate(RESPONSE_XPATH_FORMATED_ADDRESS, result,
                            XPathConstants.STRING);
                    _log.debug("formated Address (if applies) " + formatedAddress);
                    
                    String longitude = (String) xpath.evaluate(RESPONSE_XPATH_LONG, result, XPathConstants.STRING);
                    String latitude = (String) xpath.evaluate(RESPONSE_XPATH_LATITUDE, result, XPathConstants.STRING);
                    String postalCode = (String) xpath.evaluate(
                            String.format(RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_CODE),
                            result, XPathConstants.STRING);
                    String streetNumber = (String) xpath.evaluate(String.format(
                            RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_STREET_NUMBER), result,
                            XPathConstants.STRING);
                    String route = (String) xpath.evaluate(
                            String.format(RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_ROUTE),
                            result, XPathConstants.STRING);
                    String city = (String) xpath.evaluate(
                            String.format(RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_CITY),
                            result, XPathConstants.STRING);
                    String region = (String) xpath.evaluate(
                            String.format(RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_REGION),
                            result, XPathConstants.STRING);
                    String country = (String) xpath.evaluate(
                            String.format(RESPONSE_XPATH_ADDRESS_COMPONENT_BY_TYPE, RESPONSE_XPATH_POSTAL_COUNTRY),
                            result, XPathConstants.STRING);
                    
                    Address resultAddress = new Address();
                    resultAddress.setStreet(String.format(STREET_ADDRESS_FORMAT, streetNumber, route));
                    resultAddress.setPostalCode(postalCode);
                    resultAddress.setCity(city);
                    resultAddress.setRegion(region);
                    resultAddress.setCountry(country);
                    resultAddress.setLongitude(longitude);
                    resultAddress.setLatitude(latitude);
                    _log.debug("result Address " + resultAddress);
                    
                    completeAddressWithLiferayIds(resultAddress);
                    
                    response.add(resultAddress);
                }
            }
            // If something happened
            else if (!status.equals(RESPONSE_RESULT_ZERO_RESULTS)) {
                throw new SystemException("Received status from google geocode: " + status);
            }
            
        } catch (XPathExpressionException e) {
            throw new SystemException("Issues with XPath expressions: " + e.getMessage());
        }
        
        return response;
    }
    
    private void completeAddressWithLiferayIds(Address address) {
        try {
            
            if (!address.getCountry().isEmpty()) {
                
                Country country = CountryServiceUtil.fetchCountryByA2(address.getCountry());
                
                address.setCountryId(String.valueOf(country.getCountryId()));
                
                if (!address.getRegion().isEmpty()) {
                    
                    Region region = RegionServiceUtil.getRegion(country.getCountryId(), address.getRegion());
                    address.setRegionId(String.valueOf(region.getRegionId()));
                }
            }
                       
        } catch (Exception e) {
            _log.info(e.getLocalizedMessage());
            // nothing is set
        }
    }
    
    @Override
    public List<Address> validateAddress(Address address) throws Exception {
        
        String requestURL = getRequestURL(address);
        _log.debug("request URL " + requestURL);
        
        try {
            URL url = new URL(requestURL);
            
            Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
            
            return getAddressesFromXML(resultDocument);
            
        } catch (Exception e) {
            _log.error(e);
            throw e;
        }
        
    }
    
}
