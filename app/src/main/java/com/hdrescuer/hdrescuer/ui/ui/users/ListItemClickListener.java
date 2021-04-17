package com.hdrescuer.hdrescuer.ui.ui.users;

/**
 * Interfaz para gestionar el click en la lista de usuarios y saber qué item se ha pulsado
 * @author Domingo Lopez
 */
public interface ListItemClickListener {

    /**
     * Método que actúa cuando se pulsa un usuario de la lista
     * @author Domingo Lopez
     * @param position
     */
    void onListItemClick(int position);


}
