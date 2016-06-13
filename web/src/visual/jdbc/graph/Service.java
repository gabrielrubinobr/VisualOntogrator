package visual.jdbc.graph;

import visual.jdbc.executor.CypherExecutor;
import visual.jdbc.executor.JdbcCypherExecutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.neo4j.helpers.collection.MapUtil.map;

/**
 * @author Gabriel Rubino
 * 
 */
public class Service {

    private final CypherExecutor cypher;

    public Service(String uri) {
        cypher = createCypherExecutor(uri);
    }

    private CypherExecutor createCypherExecutor(String uri) {
        try {
            String auth = new URL(uri).getUserInfo();
            if (auth != null) {
                String[] parts = auth.split(":");
                return new JdbcCypherExecutor(uri,parts[0],parts[1]);
            }
            return new JdbcCypherExecutor(uri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Neo4j-ServerURL " + uri);
        }
    }
  
    String cypherQuery = "MATCH (g:Gene)-->(o:Ontology) RETURN  o.KeywordID)as ontology, collect(g.LocusTagas) as gene,  LIMIT 10";
      @SuppressWarnings("unchecked")
    public Map<String, Object> rede(String query) {
        Iterator<Map<String,Object>> result = cypher.query(query, map());
        List nodes = new ArrayList();
        List rels= new ArrayList();
        //int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            Map<String, Object> ontologymap = map("title",row.get("ontology"),"label","ontology");
          
            int target = nodes.indexOf(ontologymap);
                if (target == -1) {
                    nodes.add(ontologymap);
                    target = nodes.size()-1;
                }
            
            //i++;
            for (Object name : (Collection) row.get("gene")) {
                String relation = (String)row.get("relation");
                Map<String, Object> genemap = map("title", name,"label","gene");
                int source = nodes.indexOf(genemap);
                if (source == -1) {
                    nodes.add(genemap);
                    source = nodes.size()-1;
                }
                rels.add(map("source",source,"target",target,"relation",relation));
            }
        }
        return map("nodes", nodes, "links", rels);
    }
    
    
//[AT5G67100]	PO:0009032
//[AT5G67100]	PO:0009046
//[AT5G67100]	PO:0009010
//[AT3G05480]	PO:0009032
//[AT3G05480]	PO:0009046
//[AT3G05480]	PO:0009010
//[AT3G05480]	GO:0006281
//[AT3G52115]	GO:0006281
    
  
}
