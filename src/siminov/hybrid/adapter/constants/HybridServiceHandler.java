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

package siminov.hybrid.adapter.constants;

public interface HybridServiceHandler {

	public String TRIGGERED_EVENT = "TRIGGERED_EVENT";

	public String ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";
	
	public String ISERVICE_ON_SERVICE_START = "onServiceStart";

	public String ISERVICE_ON_SERVICE_QUEUE = "onServiceQueue";

	public String ISERVICE_ON_SERVICE_PAUSE = "onServicePause";
	
	public String ISERVICE_ON_SERVICE_RESUME = "onServiceResume";
	
	public String ISERVICE_ON_SERVICE_FINISH = "onServiceFinish";
	
	public String ISERVICE_ON_SERVICE_API_INVOKE = "onServiceApiInvoke";
	
	public String ISERVICE_ON_SERVICE_API_FINISH = "onServiceApiFinish";
	
	public String ISERVICE_ON_SERVICE_TERMINATE = "onServiceTerminate";
	
	public String ISERVICE_RESOURCES = "RESOURCES";
	
	public String ISERVICE_RESOURCE = "RESOURCE";
}
