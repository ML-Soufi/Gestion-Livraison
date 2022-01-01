#include<string>
using namespace std;
// Définition de la class Ville
class Ville{
	int code;
	string nom;
	int nb_j;
	public:
        Ville();
		Ville(int code,string nom,int nb_j);
		Ville saisie();
		void affichage();
		bool comparer(int code);
		int GetCode();
		int Getnb_j();
};

// Définition de la class Circuit
class Circuit{
	string nomc;
	int nbr_v;
	Ville tville[7];
    public:
        circuit();
        circuit(string nomc,int nbr_v,Ville t[]);
        void ajouter();
        bool verifier(Ville v1);
};