package com.mayreh.intellij.plugin.tlaplus.run.parsing;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Optional;

import org.junit.Assert;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.Range;
import com.mayreh.intellij.plugin.tlaplus.TestUtils;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.FunctionValue;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.PrimitiveValue;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.RecordValue;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.SetValue;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.SimpleErrorTrace;
import com.mayreh.intellij.plugin.tlaplus.run.parsing.TLCEvent.ErrorTraceEvent.TraceVariable;

public class TLCErrorTraceParserTest extends BasePlatformTestCase {
    public void testBasic() {
        Optional<ErrorTraceEvent> result = new TLCErrorTraceParser().parse(
                TestUtils.resourceToString("tlc/errortrace/fixtures/basic.out")
                         .lines().collect(toList()));
        Assert.assertEquals(new SimpleErrorTrace(
                2,
                "PaxosCommit",
                "RMPrepare",
                new Range<>(new SourceLocation(118, 2), new SourceLocation(121, 21)),
                asList(
                        new TraceVariable("msgs", new SetValue(asList(
                                new RecordValue(asList(
                                        new RecordValue.Entry("type", new PrimitiveValue("\"phase2a\"")),
                                        new RecordValue.Entry("ins", new PrimitiveValue("r1")),
                                        new RecordValue.Entry("bal", new PrimitiveValue("0")),
                                        new RecordValue.Entry("val", new PrimitiveValue("\"prepared\""))
                                ))
                        ))),
                        new TraceVariable("rmState", new FunctionValue(asList(
                                new FunctionValue.Entry("r1", new PrimitiveValue("\"prepared\"")),
                                new FunctionValue.Entry("r2", new PrimitiveValue("\"working\"")))
                        )),
                        new TraceVariable("aState", new FunctionValue(asList(
                                new FunctionValue.Entry("r1", new FunctionValue(asList(
                                        new FunctionValue.Entry("a1", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        ))),
                                        new FunctionValue.Entry("a2", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        ))),
                                        new FunctionValue.Entry("a3", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        )))
                                ))),
                                new FunctionValue.Entry("r2", new FunctionValue(asList(
                                        new FunctionValue.Entry("a1", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        ))),
                                        new FunctionValue.Entry("a2", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        ))),
                                        new FunctionValue.Entry("a3", new RecordValue(asList(
                                                new RecordValue.Entry("bal", new PrimitiveValue("-1")),
                                                new RecordValue.Entry("mbal", new PrimitiveValue("0")),
                                                new RecordValue.Entry("val", new PrimitiveValue("\"none\""))
                                        )))
                                )))
                        )))
        )), result.get());
    }

    public void testNonIdentifierDomainFunction() {
        Optional<ErrorTraceEvent> result = new TLCErrorTraceParser().parse(
                TestUtils.resourceToString("tlc/errortrace/fixtures/non_identifier_domain_function.out")
                         .lines().collect(toList()));
        Assert.assertEquals(new SimpleErrorTrace(
                5,
                "ProcessorSpec",
                "OnPartitionsAssigned",
                new Range<>(new SourceLocation(76, 4), new SourceLocation(89, 99)),
                asList(
                        new TraceVariable("assignedPartitions", new SetValue(asList(
                                new PrimitiveValue("2"),
                                new PrimitiveValue("3")
                        ))),
                        new TraceVariable("reloadRequested", new PrimitiveValue("TRUE")),
                        new TraceVariable("currentRebalance", new PrimitiveValue("NULL")),
                        new TraceVariable("pc", new PrimitiveValue("\"L2\"")),
                        new TraceVariable("partitionContexts", new FunctionValue(asList(
                                new FunctionValue.Entry("2", new RecordValue(asList(
                                        new RecordValue.Entry("revoked", new PrimitiveValue("FALSE")),
                                        new RecordValue.Entry("pendingRecords", new PrimitiveValue("0")),
                                        new RecordValue.Entry("paused", new PrimitiveValue("FALSE"))
                                ))),
                                new FunctionValue.Entry("3", new RecordValue(asList(
                                        new RecordValue.Entry("revoked", new PrimitiveValue("FALSE")),
                                        new RecordValue.Entry("pendingRecords", new PrimitiveValue("0")),
                                        new RecordValue.Entry("paused", new PrimitiveValue("FALSE"))
                                )))
                        )))
                )), result.get());
    }
}
