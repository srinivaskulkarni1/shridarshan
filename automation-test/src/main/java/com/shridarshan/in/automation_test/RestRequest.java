package com.shridarshan.in.automation_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.shridarshan.in.pojo.Temple;
import com.shridarshan.in.util.DBConstants;

public class RestRequest /* extends DoFixture */{

	private String hostUrl;

	private String resourcePath;

	private String requestType;
	
	private String hostName;
	
	private String port;
	
	private String entityType;
	
	private static final String HTTP_PREFIX = "http://";

	public RestRequest() {
	}

	public RestRequest(String requestType, String hostName, String port, String resourcePath, String entityType) {
		this.requestType = requestType;
		this.hostUrl = HTTP_PREFIX + hostName + ":" + port;
		this.resourcePath = resourcePath;
		this.setEntityType(entityType);
	}

	public List<Object> query() {
		List<RestResponseObject> restResponseObjectsList = null;

		if (requestType.equals("GET")) {
			restResponseObjectsList = invokeGETRequest(hostUrl, resourcePath);
		}
		return Utility.getCollection(restResponseObjectsList);
	}

	public List<RestResponseObject> invokeGETRequest(String hostUrl,
			String resourcePath) {
		List<RestResponseObject> response = new ArrayList<RestResponseObject>();
		RestClient restClient = new RestClient(requestType);
		restClient.invokeRestCall();
		response.add(restClient.getResponseObj());
		return response;
	}

	private class RestClient implements Callable<RestResponseObject> {

		private String httpMethod;

		@SuppressWarnings("unused")
		public RestClient() {
			this.httpMethod = "GET";
		}

		public RestClient(String httpMethod) {
			this.httpMethod = httpMethod;
		}

		private RestResponseObject responseObj = new RestResponseObject();

		@Override
		public RestResponseObject call() {
			invokeRestCall();
			return responseObj;
		}

		public void invokeRestCall() {
			StringWriter writer = null;
			try {
				responseObj = new RestResponseObject();

				HttpClient httpclient = new DefaultHttpClient();
				HttpUriRequest httpUriRequest;
				httpUriRequest = getHttpUriRequest(hostUrl + resourcePath);
				HttpResponse response = httpclient.execute(httpUriRequest);

				responseObj.setCode(String.valueOf(response.getStatusLine()
						.getStatusCode()));
				System.out.println("Response is "
						+ response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				writer = new StringWriter();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						content));

				StringBuilder builder = new StringBuilder();

				String line;
				while ((line = br.readLine()) != null) {
					builder.append(line);
				}

				responseObj.setJsonResponse(builder.toString());
				updateCache(responseObj);

			} catch (ClientProtocolException e) {
				System.out
						.println("Error in RestRequest.RestClient.invokeRestCall(): "
								+ "ClientProtocolException");
				e.printStackTrace();
			} catch (IOException e) {
				System.out
						.println("Error in RestRequest.RestClient.invokeRestCall(): "
								+ "IOException");
				e.printStackTrace();
			} finally {
				try {
					if (null != writer) {
						writer.close();
					}
				} catch (IOException e) {
					System.out
							.println("Error in RestRequest.RestClient.invokeRestCall().finally: "
									+ "IOException");
					e.printStackTrace();
				}
			}
		}

		private void updateCache(RestResponseObject responseObj) {
			Gson gson = new Gson();
			if (entityType.equalsIgnoreCase(DBConstants.TABLE_TEMPLE)) {
				Temple[] temples = gson.fromJson(responseObj.getJsonResponse(),
						Temple[].class);

				//List<Temple> list = Arrays.asList(temples);
				List<Temple> list = new ArrayList<Temple>();
				Collections.addAll(list, temples);
				DataCache.getCache().add(entityType, list);

			}
		}

		/*
		 * private void updateCache(RestResponseObject responseObj) { File file
		 * = createFile(responseObj.getResponseXml());
		 * 
		 * JAXBContext jaxbContext; try { if
		 * (("ContentMetadataType").equals(resourceType)) { jaxbContext =
		 * JAXBContext.newInstance(ContentMetadataResponseType.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * ContentMetadataResponseType responseType =
		 * (ContentMetadataResponseType) jaxbUnmarshaller .unmarshal(file);
		 * DataCacheRepository.getCacheRepository().add(resourceType,
		 * responseType.getContentMetadata()); } else if
		 * (("ContentInstanceMetadataType").equals(resourceType)) { jaxbContext
		 * = JAXBContext.newInstance(ContentInstanceMetadataResponseType.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * ContentInstanceMetadataResponseType responseType =
		 * (ContentInstanceMetadataResponseType) jaxbUnmarshaller
		 * .unmarshal(file);
		 * DataCacheRepository.getCacheRepository().add(resourceType,
		 * responseType.getContentInstanceMetadata()); } else if
		 * (("ContentGroupMetadataType").equals(resourceType)) { jaxbContext =
		 * JAXBContext.newInstance(ContentGroupMetadataResponseType.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * ContentGroupMetadataResponseType responseType =
		 * (ContentGroupMetadataResponseType) jaxbUnmarshaller .unmarshal(file);
		 * DataCacheRepository.getCacheRepository().add(resourceType,
		 * responseType.getContentGroupMetadata()); } else if
		 * (("PurchasableTitleType").equals(resourceType) ||
		 * ("PurchasableShowType").equals(resourceType) ||
		 * ("PurchasableSeasonType").equals(resourceType) ||
		 * ("PurchasableTitleType,PurchasableSeasonType").equals(resourceType)
		 * || ("PurchasableTitleType,PurchasableShowType").equals(resourceType)
		 * || ("PurchasableSeasonType,PurchasableShowType").equals(resourceType)
		 * ||
		 * ("PurchasableTitleType,PurchasableShowType,PurchasableSeasonType").
		 * equals(resourceType) ||
		 * ("ClassificationTreeType").equals(resourceType)) { jaxbContext =
		 * JAXBContext.newInstance(ContentRetrieveDataResponseType.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * ContentRetrieveDataResponseType responseType =
		 * (ContentRetrieveDataResponseType) jaxbUnmarshaller .unmarshal(file);
		 * DataCacheRepository.getCacheRepository().add(resourceType,
		 * responseType); } else if
		 * (("PurchaseOfferingType").equals(resourceType)) { jaxbContext =
		 * JAXBContext.newInstance(OfferRetrieveDataResponseType.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * OfferRetrieveDataResponseType responseType =
		 * (OfferRetrieveDataResponseType) jaxbUnmarshaller .unmarshal(file);
		 * DataCacheRepository.getCacheRepository().add(resourceType,
		 * responseType); } } catch (JAXBException e) { e.printStackTrace(); } }
		 */

		private HttpUriRequest getHttpUriRequest(String url) throws IOException {
			HttpUriRequest httpUriRequest = null;
			if (this.httpMethod.equals("GET")) {
				httpUriRequest = new HttpGet(url);
			}
			return httpUriRequest;
		}

		public RestResponseObject getResponseObj() {
			return responseObj;
		}
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
}
