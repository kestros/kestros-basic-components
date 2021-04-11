package io.kestros.cms.components.basic.content.buttongroup;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/button-group")
public class ButtonGroup extends BaseComponent {

}
