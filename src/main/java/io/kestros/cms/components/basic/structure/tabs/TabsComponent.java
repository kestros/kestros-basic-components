package io.kestros.cms.components.basic.structure.tabs;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/structure/tabs")
public class TabsComponent extends BaseComponent {

}
