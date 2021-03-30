package liquibase.integrationtest.command

import liquibase.integrationtest.command.CommandTest
import liquibase.change.ColumnConfig
import liquibase.change.core.CreateTableChange
import liquibase.integrationtest.setup.SetupChangeLogSync
import liquibase.integrationtest.setup.SetupDatabaseChangeLog
import liquibase.integrationtest.setup.SetupDatabaseStructure

[
    new CommandTest.Spec(
        command: ["futureRollbackFromTagSQL"],

        setup: [
            new SetupDatabaseStructure([
                [
                new CreateTableChange(
                    tableName: "FirstTable",
                    columns: [
                        ColumnConfig.fromName("FirstColumn")
                                    .setType("VARCHAR(255)")
                    ]
                    )
                ] as SetupDatabaseStructure.Entry,
                [
                new CreateTableChange(
                    tableName: "SecondTable",
                    columns: [
                        ColumnConfig.fromName("SecondColumn")
                                    .setType("VARCHAR(255)")
                    ]
                )
                ] as SetupDatabaseStructure.Entry,
            ]),
            new SetupDatabaseChangeLog("changelogs/hsqldb/complete/rollback.tag.changelog.xml"),
            new SetupChangeLogSync("changelogs/hsqldb/complete/rollback.tag.changelog.xml")
        ],
        arguments: [
            tag: "version_2.0"
        ],
        expectedOutput: [
            "",
        ],
        expectedResults: [
            statusMessage: "Successfully executed futureRollbackFromTagSQL",
            statusCode: 0
        ]
    )

] as CommandTest.Spec[]
