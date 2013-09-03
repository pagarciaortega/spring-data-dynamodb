/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.socialsignin.spring.data.dynamodb.repository.config;

import org.socialsignin.spring.data.dynamodb.repository.support.DynamoDBRepositoryFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.util.StringUtils;

public class DynamoDBRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {

	private static final String DEFAULT_AMAZON_DYNAMO_DB_BEAN_NAME = "amazonDynamoDB";

	@Override
	public String getRepositoryFactoryClassName() {
		return DynamoDBRepositoryFactoryBean.class.getName();
	}

	@Override
	public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
		AnnotationAttributes attributes = config.getAttributes();

		postProcess(builder, attributes.getString("amazonDynamoDBRef"), attributes.getString("dynamoDBMapperConfigRef"));

	}

	private void postProcess(BeanDefinitionBuilder builder, String amazonDynamoDBRef, String dynamoDBMapperConfigRef) {

		amazonDynamoDBRef = StringUtils.hasText(amazonDynamoDBRef) ? amazonDynamoDBRef
				: DEFAULT_AMAZON_DYNAMO_DB_BEAN_NAME;
		builder.addConstructorArgReference(amazonDynamoDBRef);

		if (StringUtils.hasText(dynamoDBMapperConfigRef)) {
			builder.addPropertyReference("dynamoDBMapperConfig", dynamoDBMapperConfigRef);
		}
	}

	@Override
	protected String getModulePrefix() {
		return "dynamoDB";
	}

}