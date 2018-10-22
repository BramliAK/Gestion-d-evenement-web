/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { DateeventUpdateComponent } from 'app/entities/dateevent/dateevent-update.component';
import { DateeventService } from 'app/entities/dateevent/dateevent.service';
import { Dateevent } from 'app/shared/model/dateevent.model';

describe('Component Tests', () => {
    describe('Dateevent Management Update Component', () => {
        let comp: DateeventUpdateComponent;
        let fixture: ComponentFixture<DateeventUpdateComponent>;
        let service: DateeventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BackendTestModule],
                declarations: [DateeventUpdateComponent]
            })
                .overrideTemplate(DateeventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DateeventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DateeventService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dateevent(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dateevent = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dateevent();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dateevent = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
