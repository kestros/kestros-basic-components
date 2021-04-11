package io.kestros.cms.components.basic.structure.breadcrumb;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/structure/breadcrumb")
public class BreadcrumbComponent extends BaseComponent {

}
