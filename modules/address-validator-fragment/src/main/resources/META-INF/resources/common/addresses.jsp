<%--
/**
 * Copyright (c) 2017 Rivet Logic Corporation. All rights reserved.
 */
 
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String className = (String)request.getAttribute("addresses.className");
long classPK = (Long)request.getAttribute("addresses.classPK");

List<Address> addresses = Collections.emptyList();

int[] addressesIndexes = null;

String addressesIndexesParam = ParamUtil.getString(request, "addressesIndexes");

if (Validator.isNotNull(addressesIndexesParam)) {
	addresses = new ArrayList<Address>();

	addressesIndexes = StringUtil.split(addressesIndexesParam, 0);

	for (int addressesIndex : addressesIndexes) {
		addresses.add(new AddressImpl());
	}
}
else {
	if (classPK > 0) {
		addresses = AddressServiceUtil.getAddresses(className, classPK);

		addressesIndexes = new int[addresses.size()];

		for (int i = 0; i < addresses.size(); i++) {
			addressesIndexes[i] = i;
		}
	}

	if (addresses.isEmpty()) {
		addresses = new ArrayList<Address>();

		addresses.add(new AddressImpl());

		addressesIndexes = new int[] {0};
	}

	if (addressesIndexes == null) {
		addressesIndexes = new int[0];
	}
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="addresses" />

<div class="alert alert-info">
	<liferay-ui:message key="street-1-and-city-are-required-fields.-postal-code-could-be-required-in-some-countries" />
</div>

<liferay-ui:error exception="<%= AddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= AddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= AddressZipException.class %>" message="please-enter-a-valid-postal-code" />
<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + className + ListTypeConstants.ADDRESS %>" message="please-select-a-type" />
<liferay-ui:error exception="<%= NoSuchRegionException.class %>" message="please-select-a-region" />

<aui:fieldset cssClass="addresses" id='<%= renderResponse.getNamespace() + "addresses" %>'>

	<%
	for (int i = 0; i < addressesIndexes.length; i++) {
		int addressesIndex = addressesIndexes[i];

		Address address = addresses.get(i);

		long countryId = ParamUtil.getLong(request, "addressCountryId" + addressesIndex, address.getCountryId());
		long regionId = ParamUtil.getLong(request, "addressRegionId" + addressesIndex, address.getRegionId());
	%>

		<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

		<div class="lfr-form-row">
			<div class="row-fields">
				<%@ include file="/common/addresses_address.jspf" %>
				<%@ include file="/common/address_validation.jspf" %> <%--custom rivet --%>
			</div>
		</div>

		<aui:script use="rivet-custom-dynamic-select"><%-- custom rivet --%>
			new RivetDynamicSelect(<%-- custom rivet --%>
				[
					{
						select: '<portlet:namespace />addressCountryId<%= addressesIndex %>',
						selectData: Liferay.Address.getCountries,
						selectDesc: 'nameCurrentValue',
						selectId: 'countryId',
						selectSort: '<%= true %>',
						selectVal: '<%= countryId %>'
					},
					{
						select: '<portlet:namespace />addressRegionId<%= addressesIndex %>',
						selectData: Liferay.Address.getRegions,
						selectDesc: 'name',
						selectId: 'regionId',
						selectVal: '<%= regionId %>'
					}
				]
			);
		</aui:script>

	<%
	}
	%>

	<aui:input name="addressesIndexes" type="hidden" value="<%= StringUtil.merge(addressesIndexes) %>" />
</aui:fieldset>

<%-- custom rivet - begin --%>
<script>
(function() {
	var MODULE_PATH = '/o/users-admin-web';
	AUI().applyConfig({
	    groups : {
	        'rivet-custom' : {
	            base : MODULE_PATH + '/js/rivet/',
	            combine : Liferay.AUI.getCombine(),
	            modules : {
	                'rivet-custom-dynamic-select' : {
	                    path : 'rivet-custom-dynamic-select.js',
	                    requires : [
                            'aui-base'
                        ]
	                },
	            },
	            root : MODULE_PATH + '/js/rivet/'
	        }
	    }
	});
})();
</script>
<%-- custom rivet - end --%>

<aui:script use="liferay-address,liferay-auto-fields,rivet-custom-dynamic-select"><%-- custom rivet --%>
	new Liferay.AutoFields(
		{
			contentBox: '#<portlet:namespace />addresses',
			fieldIndexes: '<portlet:namespace />addressesIndexes',
			namespace: '<portlet:namespace />',
			on: {
				'clone': function(event) {
					var guid = event.guid;
					var row = event.row;

					var dynamicSelects = row.one('select[data-componentType=dynamic_select]');

					if (dynamicSelects) {
						dynamicSelects.detach('change');
					}

					new RivetDynamicSelect( <%-- custom rivet --%>
						[
							{
								select: '<portlet:namespace />addressCountryId' + guid,
								selectData: Liferay.Address.getCountries,
								selectDesc: 'nameCurrentValue',
								selectId: 'countryId',
								selectSort: '<%= true %>',
								selectVal: '0'
							},
							{
								select: '<portlet:namespace />addressRegionId' + guid,
								selectData: Liferay.Address.getRegions,
								selectDesc: 'name',
								selectId: 'regionId',
								selectVal: '0'
							}
						]
					);
					
					A.one("#<portlet:namespace />validateAddressButton_"+guid).on('click', A.addressvalidationutil.initializesValidateAddressAction); <%-- custom rivet --%>
				}
			}
		}
	).render();
</aui:script>
