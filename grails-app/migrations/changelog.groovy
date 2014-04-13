databaseChangeLog = {

	changeSet(author: "arnold (generated)", id: "1397401498797-1") {
		createTable(tableName: "monitored_server") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "monitored_serPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "connection_success", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "hostname", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_error", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "port", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-2") {
		createTable(tableName: "sec_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sec_rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-3") {
		createTable(tableName: "sec_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sec_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "passwd", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-4") {
		createTable(tableName: "sec_user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-5") {
		createTable(tableName: "service_certificate_link") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "service_certiPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "monitored_server_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "x509certificate_information_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "certificate_information_chain_idx", type: "integer")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-6") {
		createTable(tableName: "x509certificate_information") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "x509certificaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "issuerdn", type: "varchar(1024)") {
				constraints(nullable: "false")
			}

			column(name: "md5fingerprint", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sha1fingerprint", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "subject_principal", type: "varchar(1024)") {
				constraints(nullable: "false")
			}

			column(name: "valid_not_after", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "valid_not_before", type: "timestamp") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-7") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "sec_user_rolePK", tableName: "sec_user_role")
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-12") {
		createIndex(indexName: "name_uniq_1397401498726", tableName: "monitored_server", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-13") {
		createIndex(indexName: "authority_uniq_1397401498732", tableName: "sec_role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-14") {
		createIndex(indexName: "username_uniq_1397401498736", tableName: "sec_user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-15") {
		createIndex(indexName: "md5fingerprint_uniq_1397401498743", tableName: "x509certificate_information", unique: "true") {
			column(name: "md5fingerprint")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-16") {
		createIndex(indexName: "sha1fingerprint_uniq_1397401498743", tableName: "x509certificate_information", unique: "true") {
			column(name: "sha1fingerprint")
		}
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-8") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "sec_user_role", constraintName: "FK7DE039FCD3E90052", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sec_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-9") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "sec_user_role", constraintName: "FK7DE039FC7913C432", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sec_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-10") {
		addForeignKeyConstraint(baseColumnNames: "monitored_server_id", baseTableName: "service_certificate_link", constraintName: "FK9849C9EC336B95D4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "monitored_server", referencesUniqueColumn: "false")
	}

	changeSet(author: "arnold (generated)", id: "1397401498797-11") {
		addForeignKeyConstraint(baseColumnNames: "x509certificate_information_id", baseTableName: "service_certificate_link", constraintName: "FK9849C9EC7DF6A130", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "x509certificate_information", referencesUniqueColumn: "false")
	}
}
