/*
﻿Developed with the contribution of the European Commission - Directorate General for Maritime Affairs and Fisheries
© European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can
redistribute it and/or modify it under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or any later version. The IFDM Suite is distributed in
the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. You should have received a
copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 */

package se.havochvatten.vessel.proxy.cache.mapper;

import java.util.ArrayList;
import java.util.List;
import eu.europa.ec.fisheries.uvms.asset.client.model.Asset;
import eu.europa.ec.fisheries.uvms.asset.client.model.AssetBO;
import eu.europa.ec.fisheries.uvms.asset.client.model.ContactInfo;
import se.havochvatten.service.client.vesselcompws.v2_0.GetVesselAndOwnerListByIdResponse;
import se.havochvatten.service.client.vesselcompws.v2_0.orgpers.OrganisationType;
import se.havochvatten.service.client.vesselcompws.v2_0.orgpers.RolePersonType;
import se.havochvatten.service.client.vesselcompws.v2_0.vessel.OwnerType;
import se.havochvatten.service.client.vesselcompws.v2_0.vessel.Vessel;

public class ResponseMapper {

    public static AssetBO mapToAsset(GetVesselAndOwnerListByIdResponse vesselAndOwnerListByIdResponse){

        AssetBO assetBo = new AssetBO();
        
        Vessel vessel = vesselAndOwnerListByIdResponse.getVessel();
        Asset asset = new Asset();

        asset.setActive(vessel.isActive());
        asset.setCfr(vessel.getCfr());
        asset.setFlagStateCode(vessel.getIso3AlphaNation());
        asset.setName(vessel.getVesselName());
        asset.setExternalMarking(vessel.getDistrict());
        asset.setGrossTonnage(vessel.getEuTon().doubleValue());
        asset.setLengthOverAll(vessel.getLoa().doubleValue());
        asset.setPortOfRegistration(vessel.getDefaultPort()!=null ? vessel.getDefaultPort().getPort() : null);
        asset.setImo(vessel.getImoNumber());
        asset.setIrcs(vessel.getIrcs());
        asset.setIrcsIndicator(vessel.getIrcs() !=null);
        asset.setSource("NATIONAL");
        asset.setGrossTonnage(vessel.getEuTon().doubleValue());
        asset.setLengthOverAll(vessel.getLoa().doubleValue());
        asset.setPowerOfMainEngine(vessel.getEnginePower().doubleValue());
        //asset.setHasLicense(vessel.getOwner().getAuthorizationAndLicenses().isEmpty() ? false : true);
        //asset.setMmsiNo(vessel.get);
        //asset.setLengthBetweenPerpendiculars();
        //asset.setLicenseType();
        //asset.setProducer();

        assetBo.setAsset(asset);
        
        List<ContactInfo> contacts = new ArrayList<>();

        List<OwnerType> owners = vesselAndOwnerListByIdResponse.getOwner();
        for (OwnerType owner : owners) {
            OrganisationType organisation = owner.getOrganisation();
            RolePersonType rolePerson = owner.getRolePerson();

            ContactInfo contactInfo = null;
            if (rolePerson != null) {
                contactInfo = mapToContactInfo(rolePerson);
            } else if (organisation != null) {
                contactInfo = mapToContactInfo(organisation);
            }
            contacts.add(contactInfo);
        }
        assetBo.setContacts(contacts);

        return assetBo;
    }

    private static ContactInfo mapToContactInfo(RolePersonType rolePerson) {
        ContactInfo assetContact = new ContactInfo();
        assetContact.setEmail(rolePerson.getEmail());
        assetContact.setName(rolePerson.getPersonAdress().getName().getGivenname() + " " + rolePerson.getPersonAdress().getName().getSurname());
        assetContact.setPhoneNumber(rolePerson.getHomePhone() != null ? rolePerson.getHomePhone().getTelephoneNumber() : rolePerson.getMobilePhone().getTelephoneNumber());
        return assetContact;
    }

    private static ContactInfo mapToContactInfo(OrganisationType organisationType) {
        ContactInfo assetContact = new ContactInfo();
        assetContact.setEmail(organisationType.getEmail());
        assetContact.setName(organisationType.getOrganisationAdress().getOrgName());
        assetContact.setPhoneNumber(organisationType.getPhone1()!=null ? organisationType.getPhone1().getTelephoneNumber() : null);
        return assetContact;
    }
}
