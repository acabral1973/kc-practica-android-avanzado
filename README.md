<p align="center"><img width=30% src="https://raw.githubusercontent.com/acabral1973/kc-practica-android-avanzado/master/app/src/main/res/drawable/madrid_shops_logo.png"></p>

# Madrid Shops - Pr�ctica Android Avanzado (Autor: Alejandro Cabral)

Este repo corresponde al c�digo de la pr�ctica de Android Avanzado (App MadridShops). me he basado en la aplicaci�n que fuimos desarrollando durante el curso, con las siguientes modificaciones.

## Generalizaci�n
He generalizado el m�dulo **app**, mediante dos fragmentos gen�ricos, uno para mostrar mapas y otro para mostrar datos en un RecyclerView.  
Al bajar a las siguientes capas (m�dulos **domain** y **repository**) no vi l�gico generalizar, ya que el c�digo funcionaba perfectamente tanto para tiendas como para actividades con solo cambiar la url de la API que sirve los datos.  
Debido a ello, llegu� a la conclusi�n de que lo m�s l�gico era parametrizar la solicitud de datos a **domain** y luego hacer lo propio con la solicitud a **repository**, indicando qu� datos necesitaba cargar en los fragmentos. Si se solicitaban datos de tipo SHOP, se descargaban usando la *url* de *Shops* y sis e solicitaban datos de tipo ACTIVITY, se proced�a de igual forma con la *url* de *Activities* (ambas *urls* se configuraron como variables en el *gradle* del m�dulo **repository**.  
De la misma forma se procedi� para cachear los datos en la base de datos, cre�ndose una tabla para *shops* (SHOP) y otra para *activities* (ACTIVITY).  

## Pantalla principal
La pantalla principal est� controlada por un *ViewSwitcher* que muestra una *Progress Bar* mientras se obtienen los datos, pasando a la pantalla original basada en dos fragmentos (*Map* y *Recycler View*) cuando ya tiene datos que mostrar.  
El paso de **shops** a **activities** se hace mediante dos opciones de *Men�* que est�n siempre visibles en la *toolbar*.

## Licencia  
Este repositorio es de uso libre para todo el que a �l acceda. Mucha suerte y disculpe las burradas en el c�digo.
