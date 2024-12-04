/*
 */
/**
 * 
 * <p>
 * Apiscol-Seek Web Service is identified by the <em>/seek</em>
 * root path. It's a service composition responsible for coordinating the search operations in other services. .
 * 
 * 
 *  </p><ol>
 *  <li>Full text search with highlighting</li>
 *  <li>Facetting and static and dynamic fields with consideration of hierarchies</li>
 *  <li>Spellchecking and query complection</li>
 *  </ol>
 *  <section class="attention">
 *  <h2>Static and dynamic facets</h2>
 *  <p>
 *  ApiScol Seek provides two kinds of facets. 
 *  Static facets can perform filtering on any scoLOMfr field except field 9 (classification).</p>
 *  <h3>Static facets</h3>
 *  <p>Supposing you got this information in search results :
 *  <code>&lt;apiscol:static-facets name="educational.place"&gt;         
 *  &nbsp;&nbsp;&nbsp;&lt;apiscol:facet count="18"&gt;en salle de classe&lt;/apiscol:facet&gt;  
 *  &nbsp;&nbsp;&nbsp;&lt;apiscol:facet count="21"&gt;en atelier&lt;/apiscol:facet&gt;       
 *  &lt;/apiscol:static-facets&gt;
 *  </code><br/>
 *  You can send back this filtering request, a Json-style list of filtering criteriums :<br/>
 * <code> static-filters=["educational.place::en atelier"]</code><br/>
 *   It is équivalent to SQL Query :<br/>
 * <code> SELECT ... WHERE educational.place=en atelier </code><br/>
 *  It will only retain records with this scoLOMfr field :
 *  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;scoLOMfr:place&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;scoLOMfr:source&gt;scoLOMfrv1.0&lt;/scoLOMfr:source&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;scoLOMfr:value&gt;en atelier&lt;/scoLOMfr:value&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/scoLOMfr:place&gt;
 *  </code>
 *  <h3>Dynamic facets</h3>
 *  </p>
 *  <p>However, if the faceting information is concerning field 9, its structure should be more complex:
 *  <code>&lt;apiscol:dynamic-facets name="public_cible_détaillé"&gt;         
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&lt;apiscol:taxon identifier="scoLOMfr-voc-021"&gt;             
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;apiscol:entry count="14" identifier="scoLOMfr-voc-021-num-00092" label="professeur de lycée" /&gt;          
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/apiscol:taxon&gt;       
 *  &lt;/apiscol:dynamic-facets&gt;
 *  </code><br/>
 *  The filtering request is a Json-style list of filtering criteriums :<br/>
 *  <code>["public cible détaillé::scoLOMfr-voc-021::scoLOMfr-voc-021-num-00092::professeur de lycée"]</code><br/>
 *  This query only retains records with the following scoLOMfr classification field :
 *  <code>&lt;classification&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;purpose&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;scoLOMfrv1.0&lt;/source&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;public cible détaillé&lt;/value&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/purpose&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;taxonPath&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;string language="fre"&gt;scoLOMfr-voc-021&lt;/string&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/source&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;taxon&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;id&gt;scoLOMfr-voc-021-num-00092&lt;/id&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;entry&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;string language="fre"&gt;professeur de lycée&lt;/string&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/entry&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/taxon&gt;
 *  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/taxonPath&gt;
 *  &nbsp;&nbsp;&nbsp;&lt;/classification&gt;
 *  </code>
 *  </p>
 *  <h2>Hierarchical facets</h2>
 *  <p>
 *  
 *  </p>
 *  </section>
 */

package fr.ac_versailles.crdp.apiscol.seek;