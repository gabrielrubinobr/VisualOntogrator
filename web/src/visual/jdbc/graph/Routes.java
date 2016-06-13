package visual.jdbc.graph;

import CypherCreator.Cypher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class Routes implements SparkApplication {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private Service service; 


    public Routes(Service service) {
        this.service = service;
    }

    public void init() {
       
        get(new Route("/graph") {
      
            public Object handle(Request request, Response response) {
                String gene = request.queryParams("gene");
                String relacao = request.queryParams("relacao");
                String caracteristica = request.queryParams("caracteristica");
                
                //System.out.println(gene+" "+relacao+" "+caracteristica );
                // int limit = request.queryParams("limit") != null ? Integer.valueOf(request.queryParams("limit")) : 100;
                // String cypherQuery = "MATCH (g:Gene)-->(o:Ontology) RETURN collect(g.LocusTag) as gene, o.KeywordID as ontology LIMIT 10";
                //String cypherQuery = "match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology";
                String cypherQuery = Cypher.geraCypher(gene,relacao,caracteristica); 
                        System.out.println(cypherQuery);
                //"match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT5G67100'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT3G05480'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT3G52115'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT3G25520'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT3G25520'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT3G25520'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT3G25520'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'petal'})--(b:Gene{LocusTag:'AT2G37270'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'flower'})--(b:Gene{LocusTag:'AT2G37270'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'seed'})--(b:Gene{LocusTag:'AT2G37270'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology union match (n:Ontology{Keyword:'DNA repair'})--(b:Gene{LocusTag:'AT2G37270'}) return collect(b.LocusTag) as gene, n.KeywordID as ontology";

                return gson.toJson(service.rede(cypherQuery));
            }
        });
    }
}
