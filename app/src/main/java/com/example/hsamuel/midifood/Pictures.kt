package com.example.hsamuel.midifood

/**
 * Created by hsamuel on 21/09/18.
 */
class Pictures {
    var nom: String? = null
    var offres: String? = null
    var imglink: String? = null

    constructor(){}

    constructor(nom: String?, offres: String?, imglink: String?){
        this.nom = nom
        this.offres = offres
        this.imglink = imglink
    }
}