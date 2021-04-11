package io.kestros.cms.components.basic.content.accordion;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/accordion-section")
public class AccordionSection extends BaseComponent {

}
