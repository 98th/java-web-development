package by.training.taxi;

import by.training.taxi.bean.BeanRegistry;
import by.training.taxi.bean.BeanRegistryImpl;
import by.training.taxi.car.CarDaoImpl;
import by.training.taxi.car.CarServiceImpl;
import by.training.taxi.command.AboutViewCommand;
import by.training.taxi.contact.ContactDaoImpl;
import by.training.taxi.contact.ContactServiceImpl;
import by.training.taxi.contact.EditContactViewCommand;
import by.training.taxi.discount.AssignDiscountCommand;
import by.training.taxi.discount.DiscountDaoImpl;
import by.training.taxi.discount.DiscountServiceImpl;
import by.training.taxi.driver.DriverListViewCommand;
import by.training.taxi.request.*;
import by.training.taxi.location.LocationDaoImpl;
import by.training.taxi.location.LocationServiceImpl;
import by.training.taxi.contact.EditContactCommand;
import by.training.taxi.driver.DriverDaoImpl;
import by.training.taxi.driver.DriverServiceImpl;
import by.training.taxi.user.*;
import by.training.taxi.dao.*;
import by.training.taxi.validator.ContactValidator;
import by.training.taxi.validator.DiscountValidator;
import by.training.taxi.validator.DriverValidator;
import by.training.taxi.validator.UserAccountValidator;
import by.training.taxi.wallet.*;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ApplicationContext implements BeanRegistry {

    private final static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private final static Lock INITIALIZE_LOCK = new ReentrantLock();
    private static ApplicationContext INSTANCE;

    private BeanRegistry beanRegistry = new BeanRegistryImpl();

    private ApplicationContext() {

    }

    public static void initialize() {
        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("Context was already initialized");
            } else {
                ApplicationContext context = new ApplicationContext();
                context.init();
                INSTANCE = context;
                INITIALIZED.set(true);
            }
        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static ApplicationContext getInstance() {
        if (INSTANCE != null && INITIALIZED.get()) {
            return INSTANCE;
        } else {
            throw new IllegalStateException("Context wasn't initialized");
        }
    }

    @Override
    public void destroy() {
        ApplicationContext context = getInstance();
        DataSource dataSource = context.getBean(DataSource.class);
        dataSource.close();
        beanRegistry.destroy();
    }

    private void init() {
        registerDataSource();
        registerClasses();
    }

    private void registerDataSource() {
        DataSource dataSource = new DataSourceImpl();
        TransactionManager transactionManager = new TransactionManagerImpl(dataSource);
        ConnectionManager connectionManager = new ConnectionManagerImpl(transactionManager, dataSource);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager);
        registerBean(dataSource);
        registerBean(transactionManager);
        registerBean(connectionManager);
        registerBean(transactionInterceptor);
    }

    private void registerClasses() {
        registerBean(AboutViewCommand.class);
        registerBean(AssignDiscountCommand.class);
        registerBean(CarDaoImpl.class);
        registerBean(CarServiceImpl.class);
        registerBean(CancelRideCommand.class);
        registerBean(CancelRideViewCommand.class);
        registerBean(ChooseCarCommand.class);
        registerBean(ContactDaoImpl.class);
        registerBean(ContactServiceImpl.class);
        registerBean(ContactValidator.class);
        registerBean(ConfirmRequestCommand.class);
        registerBean(DeclineRideCommand.class);
        registerBean(DeleteWalletCommand.class);
        registerBean(DeleteRequestCommand.class);
        registerBean(DiscountServiceImpl.class);
        registerBean(DiscountDaoImpl.class);
        registerBean(DiscountValidator.class);
        registerBean(DriverDaoImpl.class);
        registerBean(DriverServiceImpl.class);
        registerBean(DriverListViewCommand.class);
        registerBean(DriverValidator.class);
        registerBean(EditContactCommand.class);
        registerBean(EditContactViewCommand.class);
        registerBean(FindCarCommand.class);
        registerBean(FillWalletCommand.class);
        registerBean(LockUserCommand.class);
        registerBean(LoginUserViewCommand.class);
        registerBean(LogoutUserCommand.class);
        registerBean(UpdateUserAvatarCommand.class);
        registerBean(UserProfileViewCommand.class);
        registerBean(RequirementViewCommand.class);
        registerBean(RegistrationUserViewCommand.class);
        registerBean(RegistrationUserCommand.class);
        registerBean(RequestDaoImpl.class);
        registerBean(RequestServiceImpl.class);
        registerBean(RequestListViewCommand.class);
        registerBean(SuitableDriverViewCommand.class);
        registerBean(UserAccountDaoImpl.class);
        registerBean(UserAccountServiceImpl.class);
        registerBean(UserAccountValidator.class);
        registerBean(UserListViewCommand.class);
        registerBean(LoginUserCommand.class);
        registerBean(LocationDaoImpl.class);
        registerBean(LocationServiceImpl.class);
        registerBean(WalletServiceImpl.class);
        registerBean(WalletDaoImpl.class);
        registerBean(WalletViewCommand.class);
    }

    @Override
    public <T> void registerBean(T bean) {

        this.beanRegistry.registerBean(bean);
    }

    @Override
    public <T> void registerBean(Class<T> beanClass) {
        this.beanRegistry.registerBean(beanClass);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return this.beanRegistry.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String name) {
        return this.beanRegistry.getBean(name);
    }

    @Override
    public <T> boolean removeBean(T bean) {
        return this.beanRegistry.removeBean(bean);
    }
}
