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

package com.rivetlogic.address.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.rivetlogic.address.portlet.util.AddressValidatorConstants;
import com.rivetlogic.address.service.AddressProcessingToolsLocalServiceUtil;
import com.rivetlogic.address.util.AddressValidationConstants;
import com.rivetlogic.address.model.Address;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.add-default-resource=true",
				"com.liferay.portlet.display-category=category.hidden",
				"com.liferay.portlet.layout-cacheable=true",
				"com.liferay.portlet.private-request-attributes=false",
				"com.liferay.portlet.private-session-attributes=false",
				"com.liferay.portlet.render-weight=50",
				"com.liferay.portlet.use-default-template=true",
				"javax.portlet.display-name=Address Validator",
				"javax.portlet.expiration-cache=0",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.name=" + AddressValidatorConstants.ADDRES_VALIDATOR_PORTLET_ID,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
	)
public class AddressValidatorPortlet extends MVCPortlet {

	private static final Log _log = LogFactoryUtil.getLog(AddressValidatorPortlet.class);
    private static final int INVALID_CODE = 0;
    
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		 String callbackSelection = ParamUtil.getString(renderRequest, AddressValidatorConstants.PARAMETER_CALLBACK_SELECTION, StringPool.BLANK);
	     String callbackCancel = ParamUtil.getString(renderRequest, AddressValidatorConstants.PARAMETER_CALLBACK_CANCEL, StringPool.BLANK);
	        
	        if (callbackSelection.isEmpty() || callbackCancel.isEmpty()) {
	            SessionErrors.add(renderRequest, "addressvalidator.error.callback-functions-required");
	        }
	        else{
	        
	            renderRequest.setAttribute(AddressValidatorConstants.PARAMETER_CALLBACK_SELECTION, callbackSelection);
	            renderRequest.setAttribute(AddressValidatorConstants.PARAMETER_CALLBACK_CANCEL, callbackCancel);
	            
	            Address address;
	            try {
	                address = getAddressFromRequest(renderRequest, renderResponse);
	                List<Address> matchedAddresses = AddressProcessingToolsLocalServiceUtil.matchAddress(address);
	                
	                renderRequest.setAttribute("matchedAddresses", matchedAddresses);
	                
	                String targetId = ParamUtil.getString(renderRequest, AddressValidatorConstants.PARAMETER_CALLBACK_TARGET_ID, StringPool.BLANK);
	                renderRequest.setAttribute(AddressValidatorConstants.PARAMETER_CALLBACK_TARGET_ID, targetId);
	                
	            } catch (Exception e) {
	                _log.equals(e);
	            }
	            
	            String validationAPI = AddressProcessingToolsLocalServiceUtil.getCurrentValidationAPIName();
	            renderRequest.setAttribute("currentAPI", validationAPI);
	            renderRequest.setAttribute("googleAPI", AddressValidationConstants.GOOGLE_ADDRESS_VALIDATION_API);
	        }

	        super.doView(renderRequest, renderResponse);

	}
	 private Address getAddressFromRequest(PortletRequest request, PortletResponse response) 
			 throws PortalException,
     SystemException {
     
     Address address = new Address();
     
     String street1 = ParamUtil.getString(request, AddressValidatorConstants.LIFERAY_PARAMETER_STREET_1, StringPool.BLANK);
     String street2 = ParamUtil.getString(request, AddressValidatorConstants.LIFERAY_PARAMETER_STREET_2, StringPool.BLANK);
     String street3 = ParamUtil.getString(request, AddressValidatorConstants.LIFERAY_PARAMETER_STREET_3, StringPool.BLANK);
     String fullStreet = (String.format(AddressValidatorConstants.FULL_ADDRESS_FORMAT, street1, street2, street3)).trim();
     
     address.setStreet(fullStreet);
     
     address.setCity(ParamUtil.getString(request, AddressValidatorConstants.LIFERAY_PARAMETER_CITY, StringPool.BLANK));
     address.setPostalCode(ParamUtil.getString(request, AddressValidatorConstants.LIFERAY_PARAMETER_ZIP, StringPool.BLANK).replaceAll(AddressValidatorConstants.ZIP_SPECIAL_CHAR, StringPool.BLANK));
     
     int regionId = ParamUtil.getInteger(request, AddressValidatorConstants.LIFERAY_PARAMETER_REGION_ID, INVALID_CODE);
     if (regionId != INVALID_CODE) {
         Region region = RegionServiceUtil.getRegion(regionId);
         address.setRegion(region.getRegionCode());
     }
     
     int countryId = ParamUtil.getInteger(request, AddressValidatorConstants.LIFERAY_PARAMETER_COUNTRY_ID, INVALID_CODE);
     if (countryId != INVALID_CODE) {
         Country country = CountryServiceUtil.getCountry(countryId);
         address.setCountry(country.getA2());
     }
     
     return address;
 }

	
}