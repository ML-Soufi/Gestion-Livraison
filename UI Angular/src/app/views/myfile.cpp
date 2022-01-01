#include"agence.h"
// constructeur par défaut de la classe ville :
Ville :: Ville(){
	this->code=0;
	this->nom="";
	this->nb_j=0;
}
// constructeur avec paramètre de la classe ville :
Ville :: Ville(int code, string nom, int nb_j){
	this->code=code;
	this->nom=nom;
	this->nb_j=nb_j;
}
// cette méthode pour ajouter une ville
Ville Ville :: saisie(){
	do{
	cout<<"entrer le code de la ville"<<endl;
	cin>>code;
    }while(code>40 || code<1);
    cout<<"entrer le nom de la ville"<<endl;
    cin>>nom;
    cout<<"entrer le nombre de jour"<<endl;
    cin>>nb_j;
    return new Ville(code, nom, nb_j);
}
// cette méthode pour afficher les informations d'une ville :
void Ville :: affichage(){
	cout<<"code :"<<code<<endl;
	cout<<"nom :"<<nom<<endl;
	cout<<"nombre de jour:"<<nb_j<<endl;
}
// cette méthode pour comparer 2 villes :
bool Ville :: comparer(int code){
	if(this->code == code)
	    return 0;
	else
	    return 1;
}
// cette méthode pour revoyer le code de ville :
int Ville :: GetCode(){
	return this->code;
}
// cette méthode pour revoyer le nombre de jour :
int Ville :: Getnb_j(){
	return this->nb_j;
}


//////////////////////////////////////////////////////////////////////////////////////