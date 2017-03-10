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

package com.rivetlogic.address.activator;

import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class AddressValidatorActivator implements BundleActivator {
	
	public static final String ADDRESS_LONGITUDE_FIELD = "longitude";
	public static final String ADDRESS_LATITUDE_FIELD = "latitude";
	private static final Log _log = LogFactoryUtil.getLog(AddressValidatorActivator.class);

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		createAddressExpando();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}

	private void createAddressExpando() {

		try {
			ExpandoTable expandoTable = getExpandoTable();
			ExpandoColumn latitudeColumn = createExpandoColumn(expandoTable, ADDRESS_LATITUDE_FIELD);
			ExpandoColumn longitudeColumn = createExpandoColumn(expandoTable, ADDRESS_LONGITUDE_FIELD);

			long companyId = PortalUtil.getDefaultCompanyId();
			Role user = RoleLocalServiceUtil.getRole(companyId, RoleConstants.USER);

			// Set permissions to allow regular users do changes over lat & long
			ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, ExpandoColumn.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(latitudeColumn.getColumnId()), user.getRoleId(),
					new String[] { ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE });
			ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, ExpandoColumn.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(longitudeColumn.getColumnId()), user.getRoleId(),
					new String[] { ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE });

			_log.info("Expando table for Address: " + expandoTable.toString());
			_log.info("Expando column for Address: " + latitudeColumn.toString());
			_log.info("Expando column for Address: " + longitudeColumn.toString());

		} catch (Exception e) {
			_log.error("An error occured trying to add Expando Columns for Address: " + e);
		}
	}

	private ExpandoTable getExpandoTable() throws PortalException, SystemException {

		ExpandoTable table = null;
		try {
			table = ExpandoTableLocalServiceUtil.getDefaultTable(PortalUtil.getDefaultCompanyId(),
					Address.class.getName());
		} catch (NoSuchTableException nste) {
			table = ExpandoTableLocalServiceUtil.addDefaultTable(PortalUtil.getDefaultCompanyId(),
					Address.class.getName());
		}
		return table;
	}

	private ExpandoColumn createExpandoColumn(ExpandoTable table, String columnName)
			throws PortalException, SystemException {

		ExpandoColumn column = null;
		long tableId = table.getTableId();
		try {
			column = ExpandoColumnLocalServiceUtil.addColumn(tableId, columnName, ExpandoColumnConstants.DOUBLE);
		} catch (DuplicateColumnNameException dcne) {
			column = ExpandoColumnLocalServiceUtil.getColumn(tableId, columnName);
		}
		return column;
	}
}