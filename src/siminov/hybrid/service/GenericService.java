/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.hybrid.service;

import java.util.Iterator;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.service.Service;
import siminov.connect.service.design.IService;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.Adapter;
import siminov.hybrid.adapter.constants.HybridServiceHandler;
import siminov.hybrid.events.SiminovEventHandler;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.resource.ResourceManager;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ClassUtils;

public class GenericService extends Service {

	public void onStart() {
		
		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onStart();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);

		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_START);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceStart", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onQueue() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onQueue();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);

		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_QUEUE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceQueue", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onPause() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onPause();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_PAUSE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);


		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onResume() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onResume();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_RESUME);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceResume", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onFinish() {

		siminov.connect.resource.ResourceManager conncetResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_PAUSE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onRequestInvoke(IConnectionRequest connectionRequest) {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		ResourceManager hybridResourceManager = ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onRequestInvoke(connectionRequest);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_API_INVOKE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridConnectionRequest(connectionRequest));

		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiInvoke", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onRequestFinish(IConnectionResponse connectionResponse) {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		ResourceManager hybridResourceManager = ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onRequestFinish(connectionResponse);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_API_FINISH);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);

		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridConnectionResponse(connectionResponse));
		
		
		//Event Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiFinish", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onTerminate(ServiceException serviceException) {

		siminov.connect.resource.ResourceManager conncetResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_TERMINATE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		//Add Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
	
	private IService getNativeHandler(String apiHandler) {
		
		Class<?> classObject = null;
		try {
			classObject = Class.forName(apiHandler);
		} catch(Exception exception) {
			Log.debug(ClassUtils.class.getName(), "getNativeHandlerEvent", "Exception caught while creating service native handler object, API-HANDLER: " + apiHandler + ", " + exception.getMessage());
			return null;
		}
		

		Object object = null;
		try {
			object = classObject.newInstance();
		} catch(Exception exception) {
			Log.debug(ClassUtils.class.getName(), "getNativeHandlerEvent", "Exception caught while creating service native handler object, API-HANDLER: " + apiHandler + ", " + exception.getMessage());
			return null;
		}

		return (IService) object;
	}
}
