Cloud formation and java code for testing the learning.

You must give the log-delivery group WRITE and READ_ACP permissions to the target bucket

The logical names are used to find delta in cloudformation and then at the time of creation actual ids are used and if new physical one are same as old one then cf fails. so if you are going to keep same physical id but different logical name then aws doesn't allow.

Bucket name should not contain uppercase characters

EC2 : Value () for parameter groupId is invalid. The value cannot be empty

#
Security group sg-b7ab55ca and subnet subnet-996e9ec2 belong to different networks.

#
Security group sg-0917ef74 and subnet subnet-fb7eadb3 belong to different networks.
- it means security grouup belongs to L0VPC and subnet is in L1VPC