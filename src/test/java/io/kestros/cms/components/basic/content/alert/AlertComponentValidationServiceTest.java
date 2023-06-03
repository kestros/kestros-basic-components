package io.kestros.cms.components.basic.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import io.kestros.commons.validation.services.ModelValidatorRegistrationHandlerService;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AlertComponentValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private AlertComponentValidationService alertComponentValidationService;

  private AlertComponent alertComponent;

  private ModelValidatorRegistrationHandlerService modelValidatorRegistrationHandlerService;

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros.cms");
    alertComponentValidationService = new AlertComponentValidationService();
    modelValidatorRegistrationHandlerService = mock(ModelValidatorRegistrationHandlerService.class);
  }

  @Test
  public void testGetModelValidatorRegistrationHandlerService() {
    context.registerService(ModelValidatorRegistrationHandlerService.class,
        modelValidatorRegistrationHandlerService);
    context.registerInjectActivateService(alertComponentValidationService);
    assertNotNull(alertComponentValidationService.getModelValidatorRegistrationHandlerService());
  }

  @Test
  public void testGetModelType() {
    context.registerService(ModelValidatorRegistrationHandlerService.class,
            modelValidatorRegistrationHandlerService);
    context.registerInjectActivateService(alertComponentValidationService);
    assertEquals(AlertComponent.class, alertComponentValidationService.getModelType());
  }
}