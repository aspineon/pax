# Pax

## Latest Version
0.1

## Description

A project to help you write interfaces only once, and generate the proper files in different languages.

## Pom.xml information

In order to use this plugin, add the following dependency to your pom.xml

```
<dependency>
	<groupId>ca.mestevens.java</groupId>
	<artifactId>pax</artifactId>
	<version>0.1</version>
</dependency>
```

## Documentation

### Sample JSON Format

```
{
	"javaOutputFolder": "java",
	"objcOutputFolder": "objc",
	"classes": [
		{
			"className": "HelloPerson",
			"javaMetadata": {
				"namespace": "com.example.test",
				"className": "HelloPerson",
				"outputFolder": "src/main/java/com/example/test"
			},
			"objcMetadata": {
				"className": "MESHelloPerson",
				"outputFolder": "src"
			},
			"description": "A class that lets you say hello to a person!",
			"methods": [
				{
					"name": "hello",
					"return": "string",
					"returnDescription": "A string saying hello to the person",
					"description": "Says hello to a person",
					"params": [
						{
							"type": "string",
							"name": "name",
							"description": "Name of the person you're saying hello to.",
							"modifier": "const"
						}
					]
				}
			]
		}
	]
}
```

### Output

#### Java

```
package com.example.test;

/**
 * A class that lets you say hello to a person!
 */
public interface HelloPerson {

	/**
	 * Says hello to a person
	 * @param name Name of the person you're saying hello to.
	 * @return A string saying hello to the person
	 */
	public String hello(final String name);

}
```

#### Objective-C

```
#import <Foundation/Foundation.h>

/**
 * A class that lets you say hello to a person!
 */
@protocol MESHelloPerson <NSObject>

@required

/**
 * Says hello to a person
 * @param name Name of the person you're saying hello to.
 * @return A string saying hello to the person
 */
- (NSString*)hello:(NSString* const)name;

@end
```