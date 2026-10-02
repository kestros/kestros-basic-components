/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardListSupportTest {

  @Test
  public void testRequireComponentPrerequisitesWhenUiFrameworkRetrievalThrows() throws Exception {
    BaseSlingModelDataSource dataSource = mock(BaseSlingModelDataSource.class);
    Resource resource = mock(Resource.class);
    ResourceNotFoundException cause = mock(ResourceNotFoundException.class);
    when(resource.getPath()).thenReturn("/content/list");
    when(dataSource.getResource()).thenReturn(resource);
    when(dataSource.getElementVariations(anyString(), anyString())).thenReturn(new ArrayList<>());
    when(dataSource.getLayout(anyString())).thenReturn("default");
    when(dataSource.getUiFramework()).thenThrow(cause);

    try {
      CardListSupport.requireComponentPrerequisites(dataSource);
      fail("Expected the whole-component failure to reach the caller.");
    } catch (IllegalStateException exception) {
      assertTrue(exception.getMessage().contains("resolves to no UI framework"));
      assertSame(cause, exception.getCause());
    }
  }
}
