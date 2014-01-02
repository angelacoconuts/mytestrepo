import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class App {

	public static ResultSet execParameterisedSelect(String serviceEndpoint,
			String sparqlQueryString, ArrayList<String> prefixList, Map<String, String> initialBindingSet) {

		Query query = createParameterizedQuery(sparqlQueryString, prefixList, initialBindingSet);
		
		System.out.println(query.toString());

		ResultSet results = execSelectQuery(serviceEndpoint, query);

		return results;

	}
	
	public static ResultSet execSimpleSelect(String serviceEndpoint,
			String sparqlQueryString, ArrayList<String> prefixList) {

		Query query = createSimpleQuery(sparqlQueryString, prefixList);

		ResultSet results = execSelectQuery(serviceEndpoint, query);

		return results;

	}
	
	public static Query createParameterizedQuery (String sparqlQueryString, ArrayList<String> prefixList, Map<String, String> initialBindingSet){
		
		Model model = ModelFactory.createDefaultModel();
		
		QuerySolutionMap initialBindings = new QuerySolutionMap();

		for (Map.Entry<String, String> bindingEntry : initialBindingSet.entrySet()){
			
			RDFNode bindingNode = model.createResource(bindingEntry.getValue());
			initialBindings.add(bindingEntry.getKey(), bindingNode);
			
		}

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparqlQueryString, initialBindings);

		setPrefixes(queryStr, prefixList);
		
		Query query = queryStr.asQuery();		
		return query;
		
	}
	
	public static Query createSimpleQuery (String sparqlQueryString, ArrayList<String> prefixList){

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparqlQueryString);
		
		setPrefixes(queryStr, prefixList);

		Query query = queryStr.asQuery();
		
		return query;
	}
	
	public static ResultSet execSelectQuery(String serviceEndpoint, Query query) {

		ResultSet results = null;
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				serviceEndpoint, query);

//		results = qexec.execSelect();

		return results;

	}
	
	public static ParameterizedSparqlString setPrefixes(ParameterizedSparqlString queryStr, ArrayList<String> prefixList){
		
		for(String prefix : prefixList)
			queryStr.setNsPrefix(prefix, prefixMap.get(prefix));
		
		return queryStr;
		
	}

	public static Map<String, String> prefixMap = new HashMap<String, String>();
	static{
		prefixMap.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		prefixMap.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		prefixMap.put("dbpedia-owl", "http://dbpedia.org/ontology/");
	}
	
	static String serviceEndpoint = "http://dbpedia.org/sparql";
	static String sparqlQueryString = "SELECT ?parent WHERE {?residence dbpedia-owl:isPartOf ?parent.}";
	
	public static void main(String[] args) {
		
		ArrayList<String> prefixList = new ArrayList<String>();
		
		prefixList.add("rdf");
		prefixList.add("dbpedia-owl");

		Map<String, String> bindingSet = new HashMap<String, String>();
		bindingSet.put("residence", "http://dbpedia.org/resource/Tianhe_District");
		
		execParameterisedSelect(serviceEndpoint, sparqlQueryString, prefixList, bindingSet);

	}

}
