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
package com.rivetlogic.address.config;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;

@Component(configurationPid = "com.rivetlogic.address.config.AddressValidatorConfiguration",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
        service = AddressValidatorConfigurationUtil.class
)
public class AddressValidatorConfigurationUtil {

	@SuppressWarnings("static-access")
	public static String getValidationAPI() {
	       return _getInstance()._configuration.validationAPI();
    }

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(AddressValidatorConfiguration.class, properties);
	}

	private static AddressValidatorConfigurationUtil _getInstance() {
		return _instance;
	}

	private static final AddressValidatorConfigurationUtil _instance = new AddressValidatorConfigurationUtil();
	
	private static volatile AddressValidatorConfiguration _configuration;
}
