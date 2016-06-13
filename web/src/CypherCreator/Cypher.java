package CypherCreator;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Rubino
 */
public class Cypher {
 
    public static void main(String[] args) {
        GraphFilter filtros = new GraphFilter();
        
         ArrayList<String> gene = filtros.locusTagList;
         ArrayList<String> ontology = filtros.keyWordList;
         ArrayList<String> relation = filtros.CategoryList;
        
         //gene = null;
      
        gene.add("AT5G67100");
        gene.add("AT3G05480");
        gene.add("AT3G52115");
        gene.add("AT3G25520");
        gene.add("AT2G37270");

        
        //ontology = null;
     
        ontology.add("petal");
        ontology.add("flower");
        ontology.add("seed");
        ontology.add("DNA repair");

        relation = null;
        //relation.add("plant_structure");
        //relation.add("biological_process");
        //relation.add("");

        filtros.setLocusTagList(gene);
        filtros.setKeyWordList(ontology);
        filtros.setCategoryList(relation);

         System.out.println(filter(filtros));
        
       // System.out.println(gerarCypherGene(gene));
        
        
    }

    public static String geraCypher(String gene, String relacao, String caracteristica){
    
        GraphFilter filtros = new GraphFilter();

        filtros.setLocusTagList(listaString(gene));
        filtros.setCategoryList(listaString(relacao));
        filtros.setKeyWordList(listaString(caracteristica));

        return filter(filtros);
    }

    private static ArrayList<String> listaString(String lista) {

        ArrayList<String> alista = new ArrayList<>();
        lista = lista.trim();
        String[] vlista = lista.split("\n");
               
        int tamanho = vlista.length;
        //System.out.println("tamanho"+tamanho);
        if (tamanho > 0) {
      
            for (int i = 0; i < tamanho; i++) {
                String aux = vlista[i];
                aux = aux.trim();
                if (aux.length() > 1) {
                    alista.add(aux);
                }

            }
            if (alista.size() > 0) {
                return alista;
            } else {
                return null;
            }
            
        } else {
            return null;
        }
        
    }

     private static String filter(GraphFilter filtros) {
       //match (n)-->(b:Gene{LocusTag:'AT1G14320'}) return n,b union
       
         String cypher = "";
         ArrayList<String> gene = filtros.locusTagList;
         ArrayList<String> ontology = filtros.keyWordList;
         ArrayList<String> relation = filtros.CategoryList;

         //se for nulo tamanho igual 1
         int relationSize = 1;
         int geneSize = 1;
         int ontologySize = 1;
         
         if(relation!=null)
           relationSize = relation.size();
         
         if(gene!=null)
           geneSize = gene.size();
         
         if(ontology!=null)
           ontologySize = ontology.size();
         
         for (int i = 0; i < relationSize; i++) {
             
             String relationStr="";
             if(relation!=null)
              relationStr = relation.get(i);
              
             for (int j = 0; j < geneSize; j++) {
                 String geneStr="";
                 
                 if(gene!=null)
                 geneStr = gene.get(j);
                 
                 for (int k = 0; k < ontologySize; k++) {
                     String ontologyStr="";
                     
                     if(ontology!=null)
                     ontologyStr = ontology.get(k);
                     
                     String cypherOntology = "";
                     String cypherRelation = "";
                     String cypherGene = "";
                     
                     //se nulo usar o escopo do banco
           
                     if (relation != null) {
                         relationStr = relationStr.toLowerCase();
                         cypherRelation = "[r:Annotation{Category:'" + relationStr + "'}]";
                        
                     }else{
                          cypherRelation = "[r:Annotation]";
                     }

                     //se nulo usar o escopo do banco
                     if (gene != null) {
                         geneStr = geneStr.toUpperCase();
                        cypherGene = "(b:Gene{LocusTag:'" + geneStr + "'})";
                     } else {
                          cypherGene = "(b:Gene)";
                     }

                     //se nulo usar o escopo do banco
                     if (ontology != null) {
                         String auxOntology = ontologyStr.toUpperCase();
                         if ((auxOntology.startsWith("GO:"))||(auxOntology.startsWith("PO:"))) {
                             ontologyStr = ontologyStr.toUpperCase();
                             cypherOntology = "(n:Ontology{KeywordID:'" + ontologyStr + "'})";
                         } else {
                             //ontologyStr = ontologyStr.toLowerCase();
                             cypherOntology = "(n:Ontology{Keyword:'" + ontologyStr + "'})";
                         }
                     } else {
                         cypherOntology = "(n:Ontology)";
                     }
                     
                     //variavel que vai receber todo o codigo
                     //retorna tabela
                     String retorno;
                     if ((ontologyStr.startsWith("GO:")) || (ontologyStr.startsWith("PO:"))) {
                         retorno = "return collect(b.LocusTag) as gene, n.KeywordID as ontology, r.Category as relation";
                     } else {

                         retorno = "return collect(b.LocusTag) as gene, n.Keyword as ontology, r.Category as relation";
                     }
                     
                     
                     //retorna grafo
                     //String retorno = "return n,b";
                     
                     String graph = "match "+cypherOntology+"-"+cypherRelation+"-"+cypherGene+" "+retorno;
                     //return collect(b.LocusTag) as gene, n.KeywordID as ontology

                     //se for a ultima interacao 
                     if ((i == (relationSize - 1)) && (j == (geneSize - 1)) && (k == (ontologySize - 1))) {
                         cypher = cypher + graph;
                     } else {
                         cypher = cypher + graph + " union ";

                     }

                 }
             }

         }


       return cypher;
    }
    
    private static String gerarCypherGene(ArrayList<String> lista) {
       //match (n)-->(b:Gene{LocusTag:'AT1G14320'}) return n,b union
       
      String cypher = "";
     
       for (int i = 0; i < lista.size(); i++) {
           String aux = lista.get(i);
           String graph = "match (n)--(b:Gene{LocusTag:'" + aux + "'}) return n,b";
           if (i < (lista.size() - 1)) {
               cypher = cypher + graph + " union ";
           } else {
               cypher = cypher + graph;
           }

       }
      
       
       return cypher;
    }

    private static String gerarCypherOntology(ArrayList<String> lista) {
        //match (n)-->(b:Ontology{Keyword:'root'}) return n,b union
        //match (n)<-[r:Annotation{Category:'cellular_component'}]-(b:Gene{LocusTag:'AT1G26910'}) return n,b
        String cypher = "";
  
       for (int i = 0; i < lista.size(); i++) {
           String aux = lista.get(i);
           String graph = "match (n)--(b:Ontology{Keyword:'" + aux + "'}) return n,b";
           if (i < (lista.size() - 1)) {
               cypher = cypher + graph + " union ";
           } else {
               cypher = cypher + graph;
           }

       }
       
       return cypher;  
    }
    
    private static class GraphFilter{
        ArrayList<String> locusTagList = new ArrayList<>();
        ArrayList<String> keyWordList = new ArrayList<>();
        ArrayList<String> CategoryList = new ArrayList<>();

        public ArrayList<String> getLocusTagList() {
            return locusTagList;
        }

        public void setLocusTagList(ArrayList<String> locusTagList) {
            this.locusTagList = locusTagList;
        }

        public ArrayList<String> getKeyWordList() {
            return keyWordList;
        }

        public void setKeyWordList(ArrayList<String> keyWordList) {
            this.keyWordList = keyWordList;
        }

        public ArrayList<String> getCategoryList() {
            return CategoryList;
        }

        public void setCategoryList(ArrayList<String> CategoryList) {
            this.CategoryList = CategoryList;
        }
   
        
    }
}

