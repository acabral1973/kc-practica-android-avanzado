<p align="center"><img width=30% src="https://raw.githubusercontent.com/acabral1973/kc-practica-android-avanzado/master/app/src/main/res/drawable/madrid_shops_logo.png"></p>

# Madrid Shops - Práctica Android Avanzado (Autor: Alejandro Cabral)

Este repo corresponde al código de la práctica de Android Avanzado (App MadridShops). me he basado en la aplicación que fuimos desarrollando durante el curso, con las siguientes modificaciones.

## Generalización
He generalizado el módulo **app**, mediante dos fragmentos genéricos, uno para mostrar mapas y otro para mostrar datos en un RecyclerView.  
Al bajar a las siguientes capas (módulos **domain** y **repository**) no vi lógico generalizar, ya que el código funcionaba perfectamente tanto para tiendas como para actividades con solo cambiar la url de la API que sirve los datos.  
Debido a ello, llegué a la conclusión de que lo más lógico era parametrizar la solicitud de datos a **domain** y luego hacer lo propio con la solicitud a **repository**, indicando qué datos necesitaba cargar en los fragmentos. Si se solicitaban datos de tipo SHOP, se descargaban usando la *url* de *Shops* y sis e solicitaban datos de tipo ACTIVITY, se procedía de igual forma con la *url* de *Activities* (ambas *urls* se configuraron como variables en el *gradle* del módulo **repository**.  
De la misma forma se procedió para cachear los datos en la base de datos, creándose una tabla para *shops* (SHOP) y otra para *activities* (ACTIVITY).  

## Pantalla principal
La pantalla principal está controlada por un *ViewSwitcher* que muestra una *Progress Bar* mientras se obtienen los datos, pasando a la pantalla original basada en dos fragmentos (*Map* y *Recycler View*) cuando ya tiene datos que mostrar.  
El paso de **shops** a **activities** se hace mediante dos opciones de *Menú* que están siempre visibles en la *toolbar*.

## Licencia  
Este repositorio es de uso libre para todo el que a él acceda. Mucha suerte y disculpe las burradas en el código.
