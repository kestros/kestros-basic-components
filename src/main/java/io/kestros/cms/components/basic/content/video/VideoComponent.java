package io.kestros.cms.components.basic.content.video;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/video")
public class VideoComponent extends BaseComponent {

}
