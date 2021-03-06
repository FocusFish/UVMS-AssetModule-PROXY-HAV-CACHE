package se.havochvatten.unionvms.vessel.proxy.cache.message;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.havochvatten.unionvms.vessel.proxy.cache.bean.ParameterServiceBean;
import se.havochvatten.unionvms.vessel.proxy.cache.bean.VesselServiceBean;
import se.havochvatten.unionvms.vessel.proxy.cache.constant.ParameterKey;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class TestVesselServiceBean {

    @InjectMocks
    private VesselServiceBean vesselServiceBean;

    @Mock
    private ParameterServiceBean parameterService;

    private String nationsAsStringWithSpaces = " SWE , DN K, ESP , FIN";
    private List<String> nations = Arrays.asList("SWE", "DNK", "ESP" , "FIN");


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(parameterService.getParameterValue(ParameterKey.NATIONAL_VESSEL_NATIONS)).thenReturn(nationsAsStringWithSpaces);

    }

    @Test
    public void testGetNations(){
        List<String> nationsFromDatabase = vesselServiceBean.getNationsFromDatabase();
        Assert.assertArrayEquals(nations.toArray(), nationsFromDatabase.toArray());
    }

}
