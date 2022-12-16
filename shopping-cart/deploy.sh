#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='smm-springboot'
TEMPLATE=template.yml
STACK_NAME='sergio-athena-1'
AWS_PROFILE=$1

aws cloudformation package --template-file $TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file out.yml --profile $AWS_PROFILE
aws cloudformation deploy --template-file out.yml --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM --profile $AWS_PROFILE
