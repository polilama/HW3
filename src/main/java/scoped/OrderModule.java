package scoped;

import com.google.inject.AbstractModule;
import service.OrderService;

public class OrderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OrderService.class);
    }
}
