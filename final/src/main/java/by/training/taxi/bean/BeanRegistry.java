package by.training.taxi.bean;

public interface BeanRegistry {

    <T> void registerBean(T bean);

    <T> void registerBean(Class<T> beanClass);

    <T> T getBean(Class<T> beanClass);

    <T> T getBean(String name);

    <T> boolean removeBean(T bean);

    void destroy();
}