/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

/**
 * Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<library>
	
		<property name="name">SIMINOV LIBRARY TEMPLATE</property>
		<property name="description">Siminov Library Template</property>
	
		<!-- Database Mappings -->
			<database-mappings>
				<database-mapping path="Credential.si.xml" />
			</database-mappings>
	
			 	<!-- OR -->
			 
			<database-mappings>
				<database-mapping path="siminov.orm.library.template.model.Credential" />
			</database-mappings>
		 
	</library>
	}
	
		</pre>
	</p>
	
 *
 */

function LibraryDescriptor() {

    var properties = new Dictionary();

    var databaseMappingDescriptorPaths=[];

	/**
	 * Get library name.
	 * @return
	 */
    this.getName = function() {
    	return properties.get(Constants.LIBRARY_DESCRIPTOR_NAME);
	}
	
	/**
	 * Set library name as per defined in LibraryDescriptor.si.xml
	 * @param name
	 */
    this.setName = function(name) {
    	properties.add(Constants.LIBRARY_DESCRIPTOR_NAME, name);
	}
	
	/**
	 * Get descriptor as per defined in LibraryDescriptor.si.xml
	 * @return
	 */
    this.getDescription = function() {
    	return properties.get(Constants.LIBRARY_DESCRIPTOR_DESCRIPTION);
	}
	
	/**
	 * Set description as per defined in LibraryDescritor.core.xml
	 * @param descriptor
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.LIBRARY_DESCRIPTOR_DESCRIPTION, description);
	}
	
		
	this.getProperties = function() {
		return properties.values();
	}

	this.getProperty = function(name) {
		return properties.get(name);
	}

	this.containProperty = function(name) {
		return properties.exists(name);
	}

	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}

	
	/**
	 * Get all database mapping paths as per defined in DatabaseDescriptor.si.xml file.
	 * @return Array which contain all database mapping paths.
	 */
    this.getDatabaseMappingDescriptorPaths = function() {
    	return databaseMappingDescriptorPaths;
	}
	
	/**
	 * Add database mapping path as per defined in DatabaseDescriptor.si.xml file.
	 	<p>
	 		<pre>
	 		
EXAMPLE:
	<database-descriptor>
		<database-mappings>
			<database-mapping path="Liquor-Mappings/Liquor.xml" />
			<database-mapping path="Liquor-Mappings/LiquorBrand.xml" />
		</database-mappings>
	</database-descriptor>
	
		</pre>
	</p>
	 
	 * @param databaseMappingPath Database Mapping Path.
	 */
    this.addDatabaseMappingDescriptorPath = function(databaseMappingDescriptorPath) {
    	databaseMappingDescriptorPaths.push(databaseMappingDescriptorPaths);
	}
    
}